package BugiSquad.HaksikMatnam.order.controller;

import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.order.etc.OrdersDto;
import BugiSquad.HaksikMatnam.order.etc.OrdersPostDto;
import BugiSquad.HaksikMatnam.order.service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrdersApiController {

    private final OrdersService ordersService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<DataResponse<OrdersDto>> findOrder(@RequestParam Long id,
                                                             @RequestHeader("accessToken") String token) {
        return ordersService.findOrder(id, jwtTokenProvider.getUserPk(token));
    }

    @GetMapping("/member")
    public ResponseEntity<CountDataResponse<List<OrdersDto>>> findOrdersByMember(@RequestHeader("accessToken") String token) {
        return ordersService.findOrdersByMember(jwtTokenProvider.getUserPk(token));
    }

    @PostMapping
    public ResponseEntity<NoDataResponse> postOrder(@Valid @RequestBody OrdersPostDto ordersPostDto,
                                                    @RequestHeader("accessToken") String token) {
        return ordersService.postOrder(ordersPostDto, jwtTokenProvider.getUserPk(token));
    }

    @PostMapping("/complete")
    public ResponseEntity<NoDataResponse> updateOrderToComplete(@RequestParam Long id,
                                                                @RequestHeader("accessToken") String token) {
        return ordersService.completeOrder(id, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping
    public ResponseEntity<NoDataResponse> deleteOrder(@RequestParam Long id,
                                                      @RequestHeader("accessToken") String token) {
        return ordersService.deleteOrder(id, jwtTokenProvider.getUserPk(token));
    }

}
