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
public class PromiseMemberRelation extends Timestamped {
    @Id @GeneratedValue
    @Column(name = "promise_member_relation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "promise_id")
    private Promise promise;

    @Builder
    public PromiseMemberRelation(Member member, Promise promise) {
        this.member = member;
        this.promise = promise;
    }
}
