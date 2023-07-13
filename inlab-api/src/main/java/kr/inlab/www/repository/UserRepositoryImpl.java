package kr.inlab.www.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.inlab.www.common.type.RoleType;
import kr.inlab.www.entity.QRole;
import kr.inlab.www.entity.QUser;
import kr.inlab.www.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;
    private final QRole role = QRole.role;

    public Page<User> findUsers(String nickname, Boolean isVerified, Pageable pageable) {
        BooleanExpression hasRoleUser = user.roles.any().roleType.eq(RoleType.ROLE_USER);
        BooleanExpression hasRoleAdmin = user.roles.any().roleType.eq(RoleType.ROLE_ADMIN);
        BooleanExpression nicknameFilter = nickname == null ? null : user.nickname.contains(nickname);

        BooleanExpression isVerifiedExpression = isVerified != null ? isVerified ? hasRoleUser : hasRoleUser.not() : null;
        BooleanExpression filter = Expressions.allOf(nicknameFilter, isVerifiedExpression,hasRoleAdmin.not());


        QueryResults<User> queryResults = queryFactory.selectFrom(user)
            .innerJoin(user.roles, role)
            .where(filter)
            .orderBy(user.userId.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .distinct()
            .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }
}