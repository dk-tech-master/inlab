package kr.inlab.www.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.inlab.www.dto.response.ResponseInterviewQuestionDto;
import kr.inlab.www.entity.Interview;
import kr.inlab.www.entity.InterviewQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.inlab.www.dto.response.ResponseInterviewQuestionDetailDto.InterviewQuestionDetailDto;
import static kr.inlab.www.dto.response.ResponseInterviewQuestionStartDto.InterviewQuestionStartDto;
import static kr.inlab.www.entity.QInterview.interview;
import static kr.inlab.www.entity.QInterviewQuestion.interviewQuestion;
import static kr.inlab.www.entity.QPosition.position;
import static kr.inlab.www.entity.QQuestion.question;
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

//    public List<ResponseInterviewQuestionDto> getInterviewQuestionList(Long interviewId) {
//        return queryFactory
//                .select(Projections.constructor(ResponseInterviewQuestionDto.class,
//                        interviewQuestion.interviewQuestionId,
//                        questionVersion.title,
//                        questionLevel.questionLevelName,
//                        position.positionName,
//                        questionType.questionTypeName,
//                        questionVersion.version
//                ))
//                .from(interviewQuestion)
//                .innerJoin(interviewQuestion.interview, interview)
//                .innerJoin(interviewQuestion.question, question)
//                .innerJoin(interviewQuestion.questionVersion, questionVersion)
//                .innerJoin(question.questionType, questionType)
//                .innerJoin(question.position, position)
//                .innerJoin(questionVersion.questionLevel, questionLevel)
//                .where(interview.interviewId.eq(interviewId),
//                        questionVersion.isLatest.eq(YesNo.Y))
//                .fetch();
//    }

    public List<ResponseInterviewQuestionDto> getInterviewQuestionList(Long interviewId) {
        return queryFactory
                .select(Projections.constructor(ResponseInterviewQuestionDto.class,
                        interviewQuestion.interviewQuestionId,
                        questionVersion.title,
                        questionLevel.questionLevelName,
                        position.positionName,
                        questionType.questionTypeName,
                        questionVersion.version
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

    public List<InterviewQuestionStartDto> getInterviewQuestionnaire(Interview interview) {

        return queryFactory
                .select(Projections.constructor(InterviewQuestionStartDto.class,
                        interviewQuestion.interview.title,
                        interviewQuestion.interviewQuestionId,
                        questionVersion.questionVersionId,
                        questionVersion.title,
                        questionVersion.version
                ))
                .from(interviewQuestion)
                .innerJoin(interviewQuestion.questionVersion, questionVersion)
                .where(interviewQuestion.interview.eq(interview))
                .fetch();
    }

    ;

    public InterviewQuestionDetailDto getInterviewQuestionDetailDto(InterviewQuestion interviewQuestion) {
        return queryFactory
                .select(Projections.constructor(InterviewQuestionDetailDto.class,
                        questionVersion.title,
                        questionType.questionTypeName,
                        position.positionName,
                        questionLevel.questionLevelName,
                        questionVersion.questionVersionId,
                        questionVersion.version
                ))
                .from(questionVersion)
                .innerJoin(questionVersion.question, question)
                .innerJoin(question.questionType, questionType)
                .innerJoin(question.position, position)
                .innerJoin(questionVersion.questionLevel, questionLevel)
                .where(questionVersion.eq(interviewQuestion.getQuestionVersion()))
                .fetchOne();
    }
}
