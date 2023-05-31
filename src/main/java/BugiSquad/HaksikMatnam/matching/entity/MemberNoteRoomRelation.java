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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"member_id","note_room_id"}))
public class MemberNoteRoomRelation extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "member_note_room_realation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_room_id")
    private NoteRoom noteRoom;

    private Integer noReadCount = 0;

    private String lastMessage;

    public void plusNoReadCount() {
        this.noReadCount++;
    }
    public void updateLastMessage(String message){
        this.lastMessage = message;
    }

    public void initNoReadCount() {
        this.noReadCount = 0;
    }

    @Builder
    public MemberNoteRoomRelation(Member member, NoteRoom noteRoom) {
        this.member = member;
        this.noteRoom = noteRoom;
    }
}
