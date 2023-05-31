package BugiSquad.HaksikMatnam.matching.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.matching.etc.NoteRoomStatus;
import BugiSquad.HaksikMatnam.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "note_room")
@Getter
@NoArgsConstructor
public class NoteRoom extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "note_room_id")
    private Long id;
//    private String title;
//    private String body;
//    private LocalDateTime scheduledMealTime;
//
//    @Enumerated(EnumType.STRING)
//    private GroupType groupType;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private NoteRoomStatus status;

//    @OneToMany(mappedBy = "groups")
//    private List<MemberGroupRequest> memberList = new ArrayList<>();
//    @OneToMany(mappedBy = "groups")
//    private List<Note> noteList = new ArrayList<>();
//    @OneToMany(mappedBy = "groups")
//    private List<Promise> promiseList = new ArrayList<>();
//

    @Builder
    public NoteRoom(Long id, Post post, Member member) {
        this.id = id;
        this.post = post;
        this.member = member;
        this.status = NoteRoomStatus.ACTIVATED;
    }


}
