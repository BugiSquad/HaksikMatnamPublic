package BugiSquad.HaksikMatnam.menu.controller;

import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.menu.etc.MenuFavorDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuFavorSortDto;
import BugiSquad.HaksikMatnam.menu.service.MenuFavorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu/favor")
public class MenuFavorApiController {

    private final MenuFavorService menuFavorService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<CountDataResponse<List<MenuFavorSortDto>>> getFavorListBySorting() {

        LocalDate now = LocalDate.now();
//        if (now.getMonthValue() == 1) {
//            return menuFavorService.findFavorListBySorting(now.getYear() - 1, 12);
//        } else {
//            return menuFavorService.findFavorListBySorting(now.getYear(), now.getMonthValue() - 1);
//        }
        return menuFavorService.findFavorListBySorting(now.getYear(), now.getMonthValue());
    }

    @GetMapping("/admin")
    public ResponseEntity<CountDataResponse<List<MenuFavorDto>>> getThisMonthList(@RequestHeader("accessToken") String token,
                                                                                  @RequestParam int year,
                                                                                  @RequestParam int month) {
        return menuFavorService.findMonthList(year, month, jwtTokenProvider.getUserPk(token));
    }
}
