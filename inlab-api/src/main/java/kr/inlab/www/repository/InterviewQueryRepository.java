package kr.inlab.www.repository;

import antlr.StringUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.inlab.www.common.type.InterviewQuestionStatus;
import kr.inlab.www.dto.request.RequestGetInterviewDto;
import kr.inlab.www.dto.response.ResponseInterviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import java.util.List;

import static kr.inlab.www.entity.QInterview.interview;
import static kr.inlab.www.entity.QInterviewQuestion.interviewQuestion;
import static kr.inlab.www.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class InterviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ResponseInterviewDto> getInterview(Long userId, RequestGetInterviewDto requestDto, Pageable pageable) {
        List<ResponseInterviewDto> content = getInterviewList(userId, requestDto, pageable);
        Long count = getCount(userId, requestDto);
        return new PageImpl<>(content,pageable,count);
    }

    private List<ResponseInterviewDto> getInterviewList(Long userId, RequestGetInterviewDto requestDto, Pageable pageable) {
        return queryFactory
            .select(Projections.constructor(ResponseInterviewDto.class,
                interview.interviewId,
                interview.title,
                user.nickname,
                interviewQuestion.count()
            ))
            .from(interview)
            .innerJoin(interview.user, user)
            .leftJoin(interview.questions, interviewQuestion)
            .on(interviewQuestion.interviewQuestionStatus.coalesce(InterviewQuestionStatus.DELETE).eq(InterviewQuestionStatus.ACTIVE))
            .where(
                questionTitleEq(requestDto.getInterviewTitle()),
                userIdEq(userId),
                nickNameEq(requestDto.getNickname())
            )
            .orderBy(interview.interviewId.desc())
            .groupBy(interview.interviewId)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }


    private Long getCount(Long userId, RequestGetInterviewDto requestDto) {
        return queryFactory
                .select(interview.count())
                .from(interview)
                .innerJoin(interview.user, user)
                .where(
                        questionTitleEq(requestDto.getInterviewTitle()),
                        userIdEq(userId),
                        nickNameEq(requestDto.getNickname())
                )
                .fetchOne();
    }

    private BooleanExpression questionTitleEq(String title) {;
        return (title == null || title.isEmpty()) ? null : interview.title.contains(title);
    }

    private BooleanExpression userIdEq(Long userId) {
        return isAdmin() ? null : interview.user.userId.eq(userId);
    }

    private BooleanExpression nickNameEq(String nickname) {
        return (!isAdmin() || nickname == null || nickname.isBlank()) ? null : interview.user.nickname.contains(nickname);
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;
        if (authentication != null && authentication.isAuthenticated()) {
            isAdmin = authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        }
        return isAdmin;
    }
}
