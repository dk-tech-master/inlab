package kr.inlab.www.service;

import java.util.List;

import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionDto;

public interface QuestionService {
	void createQuestion(RequestCreateQuestionDto requestDto);

	ResponseGetQuestionDto getQuestion(Long questionId);

}
