package BugiSquad.HaksikMatnam.matching.controller;


import BugiSquad.HaksikMatnam.common.response.DataResponse;
import BugiSquad.HaksikMatnam.common.response.NoDataResponse;
import BugiSquad.HaksikMatnam.config.security.JwtTokenProvider;
import BugiSquad.HaksikMatnam.matching.dto.*;
import BugiSquad.HaksikMatnam.matching.service.NoteRoomService;
import BugiSquad.HaksikMatnam.util.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static BugiSquad.HaksikMatnam.util.Access.Token;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
public class noteController {

    private final NoteRoomService noteRoomService;
    private final JwtTokenProvider jwtTokenProvider;

    /** 쪽지함 전체 목록 조회 **/
    @GetMapping("/rooms")
    public ResponseEntity<DataResponse<List<GetNoteRoomDto>>> getNoteRooms(
            @RequestHeader(Token) String token
    ) {
        return noteRoomService.getNoteRooms(jwtTokenProvider.getUserPk(token));
    }

    /** 쪽지방 나가기  **/
    @DeleteMapping("/room")
    public ResponseEntity<NoDataResponse> deleteNoteRooms(
            @RequestParam Long noteRoomId,
            @RequestHeader(Token) String token
    ) {
        return noteRoomService.deleteNoteRooms(noteRoomId,jwtTokenProvider.getUserPk(token));
    }

    /** 쪽지이력 조회 **/
    @GetMapping
    public ResponseEntity<DataResponse<Map<String,Object>>> getNotesHistory(
            @RequestParam Long noteRoomId,
            @RequestHeader(Token) String token

    ) {
        return noteRoomService.getNotesHistory(noteRoomId,jwtTokenProvider.getUserPk(token));
    }

    /** 쪽지 생성 **/
    @PostMapping
    public ResponseEntity<NoDataResponse> postNote(
            @RequestBody PostNoteDto postNoteDto,
            @RequestHeader(Token) String token

    ) {
        return noteRoomService.postNote(postNoteDto,false,jwtTokenProvider.getUserPk(token));
    }

    /**
     * 쪽지방에 속한 유저 조회
     **/
    @GetMapping("/room/members")
    public ResponseEntity<DataResponse<List<AffiliatedMemberDto>>> getAffiliatedMembers(
            @RequestParam Long noteRoomId,
            @RequestHeader(value = Token) String token
    ) {
        return noteRoomService.getAffiliatedMembers(noteRoomId,jwtTokenProvider.getUserPk(token));
    }

    /**
     * 개인 매칭 쪽지방 목록 조회
     **/
    @GetMapping("/rooms/individual")
    public ResponseEntity<DataResponse<List<AffiliatedMemberDto>>> getIndividualNoteRooms(
            @RequestParam Long postId
    ) {
        return noteRoomService.getIndividualNoteRooms(postId);
    }
}
