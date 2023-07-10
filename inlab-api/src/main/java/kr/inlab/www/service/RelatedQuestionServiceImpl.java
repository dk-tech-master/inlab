package kr.inlab.www.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inlab.www.common.exception.QuestionNotFoundException;
import kr.inlab.www.common.exception.QuestionVersionNotFoundException;
import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestRelatedQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.QuestionVersion;
import kr.inlab.www.entity.RelatedQuestion;
import kr.inlab.www.repository.QuestionRepository;
import kr.inlab.www.repository.QuestionVersionRepository;
import kr.inlab.www.repository.RelatedQuestionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelatedQuestionServiceImpl implements RelatedQuestionService {

	private final QuestionRepository questionRepository;
	private final QuestionVersionRepository questionVersionRepository;
	private final RelatedQuestionRepository relatedQuestionRepository;

	@Transactional
	@Override
	public void createRelatedQuestion(RequestCreateRelatedQuestionDto requestDto, Long questionId) {
		Question headQuestion = questionRepository.findById(questionId)
			.orElseThrow(QuestionNotFoundException::new);
		Question tailQuestion = questionRepository.findById(requestDto.getTailQuestionId())
			.orElseThrow(QuestionNotFoundException::new);

		relatedQuestionRepository.save(RelatedQuestion.builder()
			.headQuestion(headQuestion)
			.tailQuestion(tailQuestion)
			.build());
	}

	//

	@Transactional
	public ResponseListDto<ResponseGetQuestionsDto> getRelatedQuestions(RequestRelatedQuestionsDto requestDto, Long questionId) {
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), Sort.by(Sort.Direction.DESC, "relatedQuestionId"));
		Page<RelatedQuestion> questionList = relatedQuestionRepository.findByHeadQuestionQuestionId(
			questionId,
			pageable
		);
		Page<ResponseGetQuestionsDto> dtoList = questionList.map(relatedQuestion -> {
			Question question = relatedQuestion.getTailQuestion();
			QuestionVersion latestQuestionVersion = questionVersionRepository.findByQuestionAndIsLatest(question, YesNo.Y)
				.orElseThrow(QuestionVersionNotFoundException::new);

			return ResponseGetQuestionsDto.builder()
				.title(latestQuestionVersion.getTitle())
				.questionTypeId(question.getQuestionType().getQuestionTypeId())
				.questionTypeName(question.getQuestionType().getQuestionTypeName())
				.positionId(question.getPosition().getPositionId())
				.positionName(question.getPosition().getPositionName())
				.questionLevelId(latestQuestionVersion.getQuestionLevel().getQuestionLevelId())
				.questionLevelName(latestQuestionVersion.getQuestionLevel().getQuestionLevelName())
				.version(latestQuestionVersion.getVersion())
				.build();
		});
		PagingUtil pagingUtil = new PagingUtil(dtoList.getTotalElements(), dtoList.getTotalPages(), dtoList.getNumber(), dtoList.getSize());

		return new ResponseListDto<>(dtoList.getContent(), pagingUtil);
	}

	@Transactional
	@Override
	public void deleteRelatedQuestion(Long relatedQuestionId) {
		RelatedQuestion relatedQuestion = relatedQuestionRepository.findById(relatedQuestionId)
			.orElseThrow(QuestionNotFoundException::new);

		relatedQuestionRepository.delete(relatedQuestion);
	}
}
