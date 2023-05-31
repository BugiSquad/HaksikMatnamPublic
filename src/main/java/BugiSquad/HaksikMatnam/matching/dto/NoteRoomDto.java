package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.matching.entity.Post;
import BugiSquad.HaksikMatnam.matching.etc.NoteRoomStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class NoteRoomDto {

    private Long noteRoom_id;
    private PostDto post;
    private String status;
    private LocalDateTime lastModifiedAt;

    @Builder
    public NoteRoomDto(Long noteRoom_id, PostDto post, String status, LocalDateTime lastModifiedAt) {
        this.noteRoom_id = noteRoom_id;
        this.post = post;
        this.status = status;
        this.lastModifiedAt = lastModifiedAt;
    }
}
