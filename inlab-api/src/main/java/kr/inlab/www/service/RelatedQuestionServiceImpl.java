package kr.inlab.www.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.inlab.www.common.exception.QuestionNotFoundException;
import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
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

	@Transactional
	@Override
	public void deleteRelatedQuestion(Long relatedQuestionId) {
		RelatedQuestion relatedQuestion = relatedQuestionRepository.findById(relatedQuestionId)
			.orElseThrow(QuestionNotFoundException::new);

		relatedQuestionRepository.delete(relatedQuestion);
	}
}
