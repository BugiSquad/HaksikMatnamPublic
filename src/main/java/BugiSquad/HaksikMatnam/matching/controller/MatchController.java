package BugiSquad.HaksikMatnam.matching.controller;


import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.matching.dto.RequestMatchParticipationDto;
import BugiSquad.HaksikMatnam.matching.service.NoteRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchController {

    private final NoteRoomService noteRoomService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 그룹 신청 => 쪽지방 생성
     **/
    @PostMapping("/participation")
    public ResponseEntity<DataResponse<Map<String,Object>>> requestMatchParticipation(
            @RequestBody RequestMatchParticipationDto requestMatchParticipationDto,
            @RequestHeader("accessToken") String token
    ) {
        return noteRoomService.createNoteRoom(requestMatchParticipationDto,
                                              jwtTokenProvider.getUserPk(token));
    }

}
