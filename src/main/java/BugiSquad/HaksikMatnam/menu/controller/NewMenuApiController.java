package BugiSquad.HaksikMatnam.menu.controller;

import BugiSquad.HaksikMatnam.common.response.CountDataResponse;
import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.menu.etc.*;
import BugiSquad.HaksikMatnam.menu.service.NewMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/wish")
public class NewMenuApiController {

    private final NewMenuService newMenuService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<DataResponse<NewMenuDto>> getNewMenu(@RequestParam Long id) {
        return newMenuService.findNewMenu(id);
    }

    @GetMapping("/list")
    public ResponseEntity<CountDataResponse<List<NewMenuDto>>> getNewMenuList() {
        return newMenuService.findNewMenuList();
    }

    @GetMapping("/category")
    public ResponseEntity<CountDataResponse<List<NewMenuDto>>> getNewMenuListByCategory(@RequestParam MenuCategory category) {
        return newMenuService.findNewMenuListByCategory(category);
    }

    @PostMapping("/votes")
    public ResponseEntity<NoDataResponse> votesToNewMenu(@RequestParam Long menuId,
                                                         @RequestHeader("accessToken") String token) {
        return newMenuService.voteToNewMenu(menuId, jwtTokenProvider.getUserPk(token));
    }

    @GetMapping("/votes")
    public ResponseEntity<DataResponse<CheckVoteDto>> checkVoteTime(@RequestHeader("accessToken") String token) {
        return newMenuService.checkVoteTime(jwtTokenProvider.getUserPk(token));
    }

    @PostMapping
    public ResponseEntity<NoDataResponse> postNewMenu(@Valid @RequestBody NewMenuPostDto newMenuPostDto,
                                                      @RequestHeader("accessToken") String token) {
        return newMenuService.postNewMenu(newMenuPostDto, jwtTokenProvider.getUserPk(token));
    }

    @PatchMapping
    public ResponseEntity<NoDataResponse> changeNewMenu(@Valid @RequestBody NewMenuUpdateDto newMenuUpdateDto,
                                                        @RequestHeader("accessToken") String token) {
        return newMenuService.updateNewMenu(newMenuUpdateDto, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping
    public ResponseEntity<NoDataResponse> deleteNewMenu(@RequestParam Long id,
                                                        @RequestHeader("accessToken") String token) {
        return newMenuService.deleteNewMenu(id, jwtTokenProvider.getUserPk(token));
    }
}
