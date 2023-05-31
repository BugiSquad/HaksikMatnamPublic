package BugiSquad.HaksikMatnam.matching.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import BugiSquad.HaksikMatnam.matching.etc.PostStatus;
import BugiSquad.HaksikMatnam.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity(name = "my_post")
@Getter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String body;
    private LocalDateTime scheduledMealTime;

    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private PostStatus status;
    @Builder
    public Post(String title, String body, LocalDateTime scheduledMealTime, GroupType groupType, Member member) {
        this.title = title;
        this.body = body;
        this.scheduledMealTime = scheduledMealTime;
        this.groupType = groupType;
        this.member = member;
        this.status = PostStatus.ACTIVATED;
    }

    public void changeToGroup() {
        this.groupType = GroupType.GROUP;
    }

    public void changeStatusToDeleted() {
        this.status = PostStatus.DELETED;
    }
}
