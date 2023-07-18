package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestRelatedQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetRelatedQuestionsDto;

public interface RelatedQuestionService {
	void createRelatedQuestion(RequestCreateRelatedQuestionDto requestDto);

	void deleteRelatedQuestion(Long relatedQuestionId);

	ResponseListDto<ResponseGetRelatedQuestionsDto> getRelatedQuestions(RequestRelatedQuestionsDto requestDto);
}
