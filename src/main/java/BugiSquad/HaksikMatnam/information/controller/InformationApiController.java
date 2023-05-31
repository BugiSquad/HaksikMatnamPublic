package BugiSquad.HaksikMatnam.information.controller;

import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.information.etc.InformationDto;
import BugiSquad.HaksikMatnam.information.etc.InformationPostDto;
import BugiSquad.HaksikMatnam.information.etc.InformationUpdateDto;
import BugiSquad.HaksikMatnam.information.service.InformationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class InformationApiController {

    private final InformationService informationService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<DataResponse<InformationDto>> getInformation(@RequestParam Long id) {
        return informationService.findInformation(id);
    }

    @GetMapping("/list")
    public ResponseEntity<DataResponse<Page<InformationDto>>> getInformationList(Pageable pageable) {
        return informationService.findInformationList(pageable);
    }

    @PostMapping
    public ResponseEntity<NoDataResponse> postInformation(@Valid @RequestBody InformationPostDto informationPostDto,
                                                          @RequestHeader("accessToken") String token) {
        return informationService.postInformation(informationPostDto, jwtTokenProvider.getUserPk(token));
    }

    @PatchMapping
    public ResponseEntity<NoDataResponse> changeInformation(@Valid @RequestBody InformationUpdateDto informationUpdateDto,
                                                            @RequestHeader("accessToken") String token) {
        return informationService.updateInformation(informationUpdateDto, jwtTokenProvider.getUserPk(token));
    }

    @DeleteMapping
    public ResponseEntity<NoDataResponse> deleteInformation(@RequestParam Long id,
                                                            @RequestHeader("accessToken") String token) {
        return informationService.deleteInformation(id, jwtTokenProvider.getUserPk(token));
    }
}
