package BugiSquad.HaksikMatnam.member.controller;

import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.member.etc.ShoppingCartPostDto;
import BugiSquad.HaksikMatnam.member.service.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/cart")
public class ShoppingCartApiController {

    private final ShoppingCartService shoppingCartService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<NoDataResponse> postShoppingCart(@Valid @RequestBody ShoppingCartPostDto shoppingCartPostDto,
                                                           @RequestHeader("accessToken") String token) {
        return shoppingCartService.postShoppingCart(shoppingCartPostDto, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping
    public ResponseEntity<NoDataResponse> deleteShoppingCart(@RequestParam Long id,
                                                             @RequestHeader("accessToken") String token) {
        return shoppingCartService.deleteShoppingCart(id, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping("/all")
    public ResponseEntity<NoDataResponse> clearShoppingCart(@RequestHeader("accessToken") String token) {
        return shoppingCartService.clearShoppingCartByMember(jwtTokenProvider.getUserPk(token));
    }
}
