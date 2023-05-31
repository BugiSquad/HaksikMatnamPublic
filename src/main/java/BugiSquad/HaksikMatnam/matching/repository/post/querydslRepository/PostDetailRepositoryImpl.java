package BugiSquad.HaksikMatnam.matching.repository.post.querydslRepository;

import BugiSquad.HaksikMatnam.matching.dto.GetPostDto;
import BugiSquad.HaksikMatnam.matching.dto.QGetPostDto;
import BugiSquad.HaksikMatnam.matching.etc.GroupType;
import BugiSquad.HaksikMatnam.matching.etc.PostCondition;
import BugiSquad.HaksikMatnam.matching.etc.PostStatus;
import BugiSquad.HaksikMatnam.member.etc.Gender;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;


import java.time.LocalDateTime;
import java.util.List;

import static BugiSquad.HaksikMatnam.matching.entity.QNoteRoom.noteRoom;
import static BugiSquad.HaksikMatnam.matching.entity.QPost.post;
import static BugiSquad.HaksikMatnam.member.entity.QInterest.interest;
import static BugiSquad.HaksikMatnam.member.entity.QMember.member;

public class PostDetailRepositoryImpl implements PostDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostDetailRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    QGetPostDto qGetPostDto = new QGetPostDto(
            post.id,
            member.id,
            member.profileUrl,
            post,
            member.interest,
            member.name,
            member.studentId
    );


    @Override
    public Page<GetPostDto> findAllGetPostDtos(PostCondition condition, Pageable pageable) throws NoSuchFieldException, IllegalAccessException {

        QueryResults<GetPostDto> results = queryFactory
                .select(qGetPostDto)
                .from(post)
                .leftJoin(post.member, member).fetchJoin()
                .leftJoin(member.interest, interest).fetchJoin()
                .where(
                        searchByInterest(condition.getInterest()),
                        searchByMajor(condition.getDepartment()),
                        searchByGrade(condition.getGrade()),
                        searchByPriorityTime(condition.getPriorityTime()),
                        searchByGender(condition.getGender()),
                        searchByGroupType(condition.getGroupType()),
                        post.groupType.eq(GroupType.INDIVIDUAL).or(post.groupType.eq(GroupType.ORGANIZATION)),
                        post.status.eq(PostStatus.ACTIVATED)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanBuilder searchByInterest(List<String> interestArray) throws IllegalAccessException, NoSuchFieldException {
        if (ObjectUtils.isEmpty(interestArray))
            return null;
        BooleanBuilder builder = new BooleanBuilder();
        for (String interest : interestArray) {
            BooleanPath booleanPath = (BooleanPath) member.interest.getClass().getField(interest).get(member.interest);
            BooleanExpression booleanExpression = booleanPath.isTrue();
            builder.or(booleanExpression);
        }
        return builder;
    }

    private BooleanBuilder searchByMajor(List<String> majorArray) {
        if (ObjectUtils.isEmpty(majorArray))
            return null;
        BooleanBuilder builder = new BooleanBuilder();
        for (String major : majorArray) {
            builder.or(member.department.eq(major));
        }
        return builder;
    }

    private BooleanBuilder searchByGrade(List<Integer> gradeArray) {
        if (ObjectUtils.isEmpty(gradeArray))
            return null;
        BooleanBuilder builder = new BooleanBuilder();
        for (Integer grade : gradeArray) {
            builder.or(member.grade.eq(grade));
        }
        return builder;
    }

    private BooleanBuilder searchByPriorityTime(Integer priorityTime) {
        if (ObjectUtils.isEmpty(priorityTime))
            return null;
        BooleanBuilder builder = new BooleanBuilder();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.plusMinutes(priorityTime);
        builder.or(post.scheduledMealTime.after(targetTime));
        return builder;
    }

    private BooleanBuilder searchByGender(List<Gender> genderArray) {
        if (ObjectUtils.isEmpty(genderArray))
            return null;
        BooleanBuilder builder = new BooleanBuilder();
        for (Gender gender : genderArray) {
            builder.or(member.gender.eq(gender));
        }
        return builder;
    }

    private BooleanBuilder searchByGroupType(GroupType type) {
        if (ObjectUtils.isEmpty(type))
            return null;
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(post.groupType.eq(type));
        return builder;
    }
}
