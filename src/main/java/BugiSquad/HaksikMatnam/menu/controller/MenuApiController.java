package BugiSquad.HaksikMatnam.menu.controller;

import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.menu.etc.MenuCategory;
import BugiSquad.HaksikMatnam.menu.etc.MenuDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuPostDto;
import BugiSquad.HaksikMatnam.menu.etc.MenuUpdateDto;
import BugiSquad.HaksikMatnam.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuApiController {

    private final MenuService menuService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<DataResponse<MenuDto>> findMenu(@RequestParam Long id) {
        return menuService.findMenu(id);
    }

    @GetMapping("/category")
    public ResponseEntity<CountDataResponse<List<MenuDto>>> findMenusByCategory(@RequestParam MenuCategory category) {
        return menuService.findMenusByCategory(category);
    }

    @PostMapping
    public ResponseEntity<NoDataResponse> postMenu(@Valid @RequestBody MenuPostDto menuPostDto,
                                                   @RequestHeader("accessToken") String token) {
        return menuService.postMenu(menuPostDto, jwtTokenProvider.getUserPk(token));
    }

    @PatchMapping
    public ResponseEntity<NoDataResponse> updateMenu(@Valid @RequestBody MenuUpdateDto menuUpdateDto,
                                                     @RequestHeader("accessToken") String token) {
        return menuService.updateMenu(menuUpdateDto, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping
    public ResponseEntity<NoDataResponse> deleteMenu(@RequestParam Long id,
                                                     @RequestHeader("accessToken") String token) {
        return menuService.deleteMenu(id, jwtTokenProvider.getUserPk(token));
    }
}
