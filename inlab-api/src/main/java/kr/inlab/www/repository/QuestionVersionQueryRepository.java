package kr.inlab.www.repository;

import static kr.inlab.www.entity.QPosition.*;
import static kr.inlab.www.entity.QQuestion.*;
import static kr.inlab.www.entity.QQuestionLevel.*;
import static kr.inlab.www.entity.QQuestionType.*;
import static kr.inlab.www.entity.QQuestionVersion.*;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.dto.request.RequestQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionVersionQueryRepository {

	private final JPAQueryFactory queryFactory;

	public Page<ResponseGetQuestionsDto> findQuestions(
		Map<Integer, List<Integer>> groupedPositionLevels,
		RequestQuestionsDto requestQuestionsDto, Pageable pageable) {

		BooleanBuilder whereClause = new BooleanBuilder();

		groupedPositionLevels.forEach((groupPositionId, groupQuestionLevelIds) -> {
			BooleanBuilder innerWhereClause = new BooleanBuilder();

			innerWhereClause.and(position.positionId.eq(groupPositionId));
			innerWhereClause.and(questionLevel.questionLevelId.in(groupQuestionLevelIds));

			whereClause.or(innerWhereClause);
		});

		whereClause.and(questionVersion.isLatest.eq(YesNo.valueOf("Y")));

		if (requestQuestionsDto.getPositionId() != null) {
			whereClause.and(position.positionId.eq(requestQuestionsDto.getPositionId()));
		}

		if (requestQuestionsDto.getQuestionTypeId() != null) {
			whereClause.and(question.questionType.questionTypeId.eq(requestQuestionsDto.getQuestionTypeId()));
		}

		if (requestQuestionsDto.getQuestionLevelId() != null) {
			whereClause.and(questionLevel.questionLevelId.eq(requestQuestionsDto.getQuestionLevelId()));
		}

		if (requestQuestionsDto.getTitleKeyword() != null) {
			whereClause.and(questionVersion.title.like("%" + requestQuestionsDto.getTitleKeyword() + "%"));
		}

		JPAQuery<ResponseGetQuestionsDto> query = queryFactory
			.select(Projections.constructor(ResponseGetQuestionsDto.class,
				question.questionId,
				questionVersion.title,
				questionType.questionTypeId,
				questionType.questionTypeName,
				position.positionId,
				position.positionName,
				questionLevel.questionLevelId,
				questionLevel.questionLevelName,
				questionVersion.version))
			.from(questionVersion)
			.join(questionVersion.question, question)
			.join(question.position, position)
			.join(question.questionType, questionType)
			.join(questionVersion.questionLevel, questionLevel)
			.where(whereClause);

		// Apply pagination manually
		query.offset(pageable.getOffset());
		query.limit(pageable.getPageSize());

		List<ResponseGetQuestionsDto> results = query.fetch();

		return new PageImpl<>(results, pageable, query.fetchCount());
	}
}
