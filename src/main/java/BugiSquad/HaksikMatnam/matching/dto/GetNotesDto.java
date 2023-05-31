package BugiSquad.HaksikMatnam.matching.dto;

import BugiSquad.HaksikMatnam.matching.entity.Note;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetNotesDto {
    private Long noteId;
    private Long memberId;
    private String profileUrl;
    private String name;
    private String message;
    private LocalDateTime createdAt;
    private boolean firstMessage;

    public GetNotesDto(Note note) {
        this.noteId = note.getId();
        this.profileUrl = note.getSender().getProfileUrl();
        this.name = note.getSender().getName();
        this.message = note.getMessage();
        this.createdAt = note.getCreatedAt();
        this.firstMessage = note.isFirstMessage();
        this.memberId = note.getSender().getId();
    }
}
