package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.common.mapper.MyTimeConverter;
import BugiSquad.HaksikMatnam.matching.entity.NoteRoom;
import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetNoteRoomDto {
    private Long postId;
    private String title;
    private String body;
    private Long minutesLeftUntilMeal;
    private GroupType groupType;
    private Long noteRoomId;
    private Long creatorId;
//    private List<NoteRoomDetailDto> noteRoomDetailDto = new ArrayList<>();


    public GetNoteRoomDto(MemberNoteRoomRelation memberNoteRoomRelation) {
        Post post = memberNoteRoomRelation.getNoteRoom().getPost();
        this.postId = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.minutesLeftUntilMeal = MyTimeConverter.minutesFromNow(post.getScheduledMealTime());
        this.groupType = post.getGroupType();
        this.noteRoomId = memberNoteRoomRelation.getNoteRoom().getId();
        this.creatorId = post.getMember().getId();

//        for (MemberNoteRoomRelation noteRoomRelation : memberNoteRoomRelation) {
//            this.noteRoomDetailDto.add(new NoteRoomDetailDto(noteRoomRelation));
//        }
    }
}
