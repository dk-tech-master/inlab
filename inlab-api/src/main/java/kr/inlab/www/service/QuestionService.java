package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.request.RequestQuestionsDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;

public interface QuestionService {
	void createQuestion(RequestCreateQuestionDto requestDto);

	ResponseGetQuestionDto getQuestion(Long questionId);

	ResponseListDto<ResponseGetQuestionsDto> getQuestions(RequestQuestionsDto requestDto);

	void updateQuestion(RequestUpdateQuestionDto requestDto, Long questionId);
}
