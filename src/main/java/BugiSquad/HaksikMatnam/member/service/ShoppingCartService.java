package BugiSquad.HaksikMatnam.member.service;

import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.member.entity.Member;
import BugiSquad.HaksikMatnam.member.entity.ShoppingCart;
import BugiSquad.HaksikMatnam.member.etc.ShoppingCartPostDto;
import BugiSquad.HaksikMatnam.member.repository.MemberRepository;
import BugiSquad.HaksikMatnam.member.repository.ShoppingCartRepository;
import BugiSquad.HaksikMatnam.menu.entity.Menu;
import BugiSquad.HaksikMatnam.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final MenuRepository menuRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<NoDataResponse> postShoppingCart(ShoppingCartPostDto shoppingCartPostDto, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        Menu menu = menuRepository.findById(shoppingCartPostDto.getMenuId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MENU));
        ShoppingCart shoppingCartItem = ShoppingCart.builder()
                .member(member)
                .menuId(menu.getId())
                .name(menu.getName())
                .url(menu.getImageUrl())
                .counts(shoppingCartPostDto.getCounts())
                .price(menu.getPrice())
                .build();
        shoppingCartItem.initCreatedAt(LocalDateTime.now());
        shoppingCartRepository.save(shoppingCartItem);
        member.getShoppingCartList().add(shoppingCartItem);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    public ResponseEntity<NoDataResponse> deleteShoppingCart(Long id, String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_CART));
        if (!member.equals(shoppingCart.getMember())) {
            throw new CustomException(ErrorCode.INVALID_USER_CART);
        }
        shoppingCartRepository.delete(shoppingCart);

        return ResponseEntity.ok(new NoDataResponse(200));
    }

    public ResponseEntity<NoDataResponse> clearShoppingCartByMember(String tokenEmail) {

        Member member = memberRepository.findByEmail(tokenEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_MEMBER));
        shoppingCartRepository.deleteAll(member.getShoppingCartList());

        return ResponseEntity.ok(new NoDataResponse(200));
    }
}
