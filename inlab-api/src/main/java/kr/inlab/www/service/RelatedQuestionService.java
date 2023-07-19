package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestRelatedQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetRelatedQuestionsDto;
import kr.inlab.www.dto.response.ResponseListWithQuestionDto;

public interface RelatedQuestionService {
	void createRelatedQuestion(RequestCreateRelatedQuestionDto requestDto);

	void deleteRelatedQuestion(Long relatedQuestionId);

	ResponseListWithQuestionDto<ResponseGetRelatedQuestionsDto> getRelatedQuestions(RequestRelatedQuestionsDto requestDto);
}
