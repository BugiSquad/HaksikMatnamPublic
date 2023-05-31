package BugiSquad.HaksikMatnam.matching.repository.PromiseMemberRelation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class PromiseMemberRelationRepositoryImpl implements PromiseMemberRelationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PromiseMemberRelationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
