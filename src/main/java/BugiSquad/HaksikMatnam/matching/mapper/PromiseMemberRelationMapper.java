package BugiSquad.HaksikMatnam.matching.mapper;

import BugiSquad.HaksikMatnam.matching.entity.Promise;
import BugiSquad.HaksikMatnam.matching.entity.PromiseMemberRelation;
import BugiSquad.HaksikMatnam.member.entity.Member;

public class PromiseMemberRelationMapper {

    public static PromiseMemberRelation toEntity(Member member, Promise promise) {
        return PromiseMemberRelation.builder()
                .member(member)
                .promise(promise)
                .build();
    }
}
