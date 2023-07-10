package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;

public interface RelatedQuestionService {
	void createRelatedQuestion(RequestCreateRelatedQuestionDto requestDto, Long questionId);

	void deleteRelatedQuestion(Long relatedQuestionId);
}
