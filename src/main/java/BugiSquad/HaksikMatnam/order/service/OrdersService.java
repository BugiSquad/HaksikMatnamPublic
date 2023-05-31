package BugiSquad.HaksikMatnam.order.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.etc.MemberType;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import BugiSquad.HaksikMatnam.member.service.ShoppingCartService;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.repository.MenuRepository;
import BugiSquad.HaksikMatnam.mvc.dto.WaitingOrderDetailDto;
import BugiSquad.HaksikMatnam.menu.service.MenuFavorService;
import BugiSquad.HaksikMatnam.message.service.MessageService;
import BugiSquad.HaksikMatnam.order.entity.MenuOrdersItem;
import BugiSquad.HaksikMatnam.order.entity.Orders;
import BugiSquad.HaksikMatnam.order.entity.Payment;
import BugiSquad.HaksikMatnam.order.etc.*;
import BugiSquad.HaksikMatnam.order.mapper.OrdersMapper;
import BugiSquad.HaksikMatnam.order.mapper.PaymentMapper;
import BugiSquad.HaksikMatnam.order.repository.MenuOrderItemRepository;
import BugiSquad.HaksikMatnam.order.repository.OrdersRepository;
import BugiSquad.HaksikMatnam.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {

    private final FluxProcessor<Orders, Orders> eventProcessor = DirectProcessor.<Orders>create().serialize();;
    private final FluxSink<Orders> eventSink = eventProcessor.sink();
    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final MenuRepository menuRepository;
    private final MenuOrderItemRepository menuOrderItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final MessageService messageService;
    private final MenuFavorService menuFavorService;


    private ThreadLocal<Integer> threadLocalPrice = new ThreadLocal<>();
    private LocalDate currentDate;
    private AtomicInteger counter;

    private void setTotalPriceInThreadLocal(Integer price) {
        threadLocalPrice.set(price);
    }

    private int getTotalPriceByThreadLocal() {
        return threadLocalPrice.get();
    }

    private void removeTotalPriceInThreadLocal() {
        threadLocalPrice.remove();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initCounterResource() {

        currentDate = LocalDate.now();
        counter = new AtomicInteger(0);
    }

    /**
     * 호출할 떄 마다 날짜 확인, 카운터 값을 가져옴
     */
    private String generateOrderNumber() {
        LocalDate today = LocalDate.now();
        if (!today.equals(currentDate)) {
            resetCounter(today);
        }
        return String.format("%s-%04d", today.toString(), counter.incrementAndGet());
    }

    private void resetCounter(LocalDate today) {
        currentDate = today;
        counter.set(0);
    }

    public boolean checkRecordDate(LocalDate now) {
        if (currentDate.getYear() != now.getYear() || currentDate.getMonthValue() != now.getMonthValue()) {
            currentDate = LocalDate.now();
            return false;
        }
        return true;
    }

    public ResponseEntity<DataResponse<OrdersDto>> findOrder(Long id, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ORDERS));
        if (!member.equals(order.getMember())) {
            throw new CustomException(ErrorCode.INVALID_USER_ORDERS);
        }

        return ResponseEntity.ok(DataResponse.response(200, OrdersMapper.toDto(order)));
    }

    public ResponseEntity<CountDataResponse<List<OrdersDto>>> findOrdersByMember(String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        List<Orders> ordersList = ordersRepository.findAllByMember(member);

        return ordersList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(CountDataResponse.response(200, ordersList.stream().map(OrdersMapper::toDto).toList(), ordersList.size()));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> completeOrder(Long orderId, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ORDERS));
        order.completeOrder();
        order.changeModifiedAt(LocalDateTime.now());
        var payload = String.format("{ \"title\": \"%s\",\"orderId\": \"%s\", \"itemName\": \"%s\" }", "조리가 완료되었습니다.", order.getPayment().getConfirmNum(), order.getMenuOrdersItemList().get(0).getMenu().getName());
        messageService.sendNotification(order.getMember().getId(), payload);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> postOrder(OrdersPostDto ordersPostDto, String tokenEmail) {

        if (!checkRecordDate(LocalDate.now())) {
            menuFavorService.updateThisMonthList();
        }
        try {
            Member member = memberRepository.findByEmail(tokenEmail)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
            Payment payment = PaymentMapper.toEntityByPost(ordersPostDto.getPaymentPostDto(), LocalDateTime.now(), generateOrderNumber());
            payment.initCreatedAt(LocalDateTime.now());
            paymentRepository.save(payment);

            Orders order = new Orders(OrderType.valueOf(ordersPostDto.getOrdersType()), member, payment);
            List<OrderItemPostDto> postDtos = ordersPostDto.getMenuOrderItems();
            List<MenuOrdersItem> items = new ArrayList<>();
            setTotalPriceInThreadLocal(0);
            for (OrderItemPostDto postDto : postDtos) {
                Menu menu = menuRepository.findById(postDto.getMenuId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
                MenuOrdersItem item = new MenuOrdersItem(postDto.getCount(), menu.getPrice() * postDto.getCount(), order, menu);
                item.initCreatedAt(LocalDateTime.now());
                menuOrderItemRepository.save(item);
                items.add(item);
                setTotalPriceInThreadLocal(getTotalPriceByThreadLocal() + item.getSumPrice());
                menu.getCountList().get(menu.getCountList().size() - 1).plusCount(item.getCount());
            }
            order.initTotalPrice(getTotalPriceByThreadLocal());
            order.initMenuOrderItemList(items);
            order.initCreatedAt(LocalDateTime.now());
            ordersRepository.save(order);
            shoppingCartService.clearShoppingCartByMember(member.getEmail());
            eventSink.next(order);
        } finally {
            removeTotalPriceInThreadLocal();
        }

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    @Transactional
    public ResponseEntity<NoDataResponse> deleteOrder(Long id, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ORDERS));
        if (!member.equals(order.getMember())) {
            throw new CustomException(ErrorCode.INVALID_USER_ORDERS);
        }
        ordersRepository.delete(order);

        return ResponseEntity.ok(new NoDataResponse(200));
    }


    /**
     * admin 주문 거절
     **/
    @Transactional
    public ResponseEntity<NoDataResponse> deleteOrderWithAdminMvc(Long id, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));

        if (!member.getMemberType().equals(MemberType.ADMIN)) {
            throw new CustomException(ErrorCode.NOT_ADMIN);
        }

        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_ORDERS));
//        if (!member.equals(order.getMember())) {
//            throw new CustomException(ErrorCode.INVALID_USER_ORDERS);
//        }


        var payload = String.format("{ \"title\": \"%s\",\"orderId\": \"%s\", \"itemName\": \"%s\" }", "주문이 거절되었습니다..", order.getPayment().getConfirmNum(), order.getMenuOrdersItemList().get(0).getMenu().getName());
        messageService.sendNotification(order.getMember().getId(), payload);

        menuOrderItemRepository.deleteByOrdersId(order.getId());
        ordersRepository.delete(order);


        return ResponseEntity.ok(new NoDataResponse(200));
    }



    public List<WaitingOrderDetailDto> findOrderNotInCompleteMvc() {
        List<Orders> waitingOrders = ordersRepository.findWaitingOrdersNotComplete();
        List<WaitingOrderDetailDto> result = waitingOrders
                .stream()
                .map(WaitingOrderDetailDto::new)
                .collect(Collectors.toList());
        return result;
    }


    public List<WaitingOrderDetailDto> findOrderCompleteMvc() {
        List<Orders> waitingOrders = ordersRepository.findWaitingOrdersComplete();
        List<WaitingOrderDetailDto> result = waitingOrders
                .stream()
                .map(WaitingOrderDetailDto::new)
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public void changeOrderTypeMvc(Long orderId, OrderType type, String email) {
        if(type.equals(OrderType.COMPLETE)) {
            completeOrder(orderId, email);
        }else{
            Orders orders = ordersRepository.findById(orderId).get();
            orders.changeOrderType(type);
        }
    }

    public Flux<Orders> streamOrders() {
        log.info("service.streamOrders");
        return Flux.fromIterable(ordersRepository.findWaitingOrdersNotComplete())
                .concatWith(eventProcessor)
                .share();
    }
}
