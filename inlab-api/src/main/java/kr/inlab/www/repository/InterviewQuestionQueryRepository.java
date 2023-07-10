package kr.inlab.www.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.inlab.www.dto.response.ResponseInterviewQuestionDto;
import kr.inlab.www.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.inlab.www.entity.QInterview.*;
import static kr.inlab.www.entity.QInterviewQuestion.*;
import static kr.inlab.www.entity.QPosition.position;
import static kr.inlab.www.entity.QQuestion.*;
import static kr.inlab.www.entity.QQuestionLevel.questionLevel;
import static kr.inlab.www.entity.QQuestionType.questionType;
import static kr.inlab.www.entity.QQuestionVersion.questionVersion;

@Repository
@RequiredArgsConstructor
public class InterviewQuestionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean interviewQuestionExist(Long interviewId, Long questionId) {
        Integer fetchOne = queryFactory.
                selectOne()
                .from(interviewQuestion)
                .where(interviewQuestion.interview.interviewId.eq(interviewId),
                        interviewQuestion.question.questionId.eq(questionId)
                        )
                .fetchFirst();

        return fetchOne != null;
    }

    public List<ResponseInterviewQuestionDto> getInterviewQuestionList(Long interviewId) {
        return queryFactory
                .select(Projections.constructor(ResponseInterviewQuestionDto.class,
                        interviewQuestion.interviewQuestionId,
                        questionVersion.title,
                        questionLevel.questionLevelName,
                        position.positionName,
                        questionType.questionTypeName
                ))
                .from(interviewQuestion)
                .innerJoin(interviewQuestion.interview, interview)
                .innerJoin(interviewQuestion.question, question)
                .innerJoin(interviewQuestion.questionVersion, questionVersion)
                .innerJoin(question.questionType, questionType)
                .innerJoin(question.position, position)
                .innerJoin(questionVersion.questionLevel, questionLevel)
                .where(interview.interviewId.eq(interviewId))
                .fetch();
    }


}
