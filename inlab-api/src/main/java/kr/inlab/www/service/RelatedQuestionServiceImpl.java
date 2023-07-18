package kr.inlab.www.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inlab.www.common.exception.QuestionNotFoundException;
import kr.inlab.www.common.exception.RelatedQuestionDuplicateException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestRelatedQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetRelatedQuestionsDto;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.RelatedQuestion;
import kr.inlab.www.repository.QuestionRepository;
import kr.inlab.www.repository.RelatedQuestionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelatedQuestionServiceImpl implements RelatedQuestionService {

	private final QuestionRepository questionRepository;
	private final RelatedQuestionRepository relatedQuestionRepository;

	@Transactional
	@Override
	public void createRelatedQuestion(RequestCreateRelatedQuestionDto requestDto) {
		Question headQuestion = questionRepository.findById(requestDto.getHeadQuestionId())
			.orElseThrow(QuestionNotFoundException::new);
		Question tailQuestion = questionRepository.findById(requestDto.getTailQuestionId())
			.orElseThrow(QuestionNotFoundException::new);

		if (relatedQuestionRepository.existsByHeadQuestionAndTailQuestion(headQuestion, tailQuestion)) {
			throw new RelatedQuestionDuplicateException();
		}

		relatedQuestionRepository.save(RelatedQuestion.builder()
			.headQuestion(headQuestion)
			.tailQuestion(tailQuestion)
			.build());
	}

	@Transactional
	@Override
	public ResponseListDto<ResponseGetRelatedQuestionsDto> getRelatedQuestions(RequestRelatedQuestionsDto requestDto) {
		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(),
			Sort.by(Sort.Direction.DESC, "relatedQuestionId"));
		Page<RelatedQuestion> questionList = relatedQuestionRepository.findAllByHeadQuestionQuestionId(
			requestDto.getQuestionId(),
			pageable
		);

		List<ResponseGetRelatedQuestionsDto> dtoList = questionList.stream()
			.map(RelatedQuestion::toResponseRelatedQuestionsDto).collect(Collectors.toList());
		PagingUtil pagingUtil = new PagingUtil(questionList.getTotalElements(), questionList.getTotalPages(),
			questionList.getNumber(), questionList.getSize());

		return ResponseListDto.<ResponseGetRelatedQuestionsDto>builder()
			.responseList(dtoList)
			.pagingUtil(pagingUtil)
			.build();
	}

	@Transactional
	@Override
	public void deleteRelatedQuestion(Long relatedQuestionId) {
		RelatedQuestion relatedQuestion = relatedQuestionRepository.findById(relatedQuestionId)
			.orElseThrow(QuestionNotFoundException::new);

		relatedQuestionRepository.delete(relatedQuestion);
	}
}