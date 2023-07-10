package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateRelatedQuestionDto;
import kr.inlab.www.dto.request.RequestRelatedQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;

public interface RelatedQuestionService {
	void createRelatedQuestion(RequestCreateRelatedQuestionDto requestDto, Long questionId);

	void deleteRelatedQuestion(Long relatedQuestionId);

	ResponseListDto<ResponseGetQuestionsDto> getRelatedQuestions(RequestRelatedQuestionsDto requestDto, Long questionId);
}
