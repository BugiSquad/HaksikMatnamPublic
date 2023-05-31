package BugiSquad.HaksikMatnam.matching.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Note extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "note_id")
    private Long id;

    private String message;
    private boolean firstMessage;

    @OneToOne
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_room_id")
    private NoteRoom noteRoom;

    @Builder
    public Note(String message, boolean firstMessage, Member sender, NoteRoom noteRoom) {
        this.message = message;
        this.firstMessage = firstMessage;
        this.sender = sender;
        this.noteRoom = noteRoom;
    }
}
