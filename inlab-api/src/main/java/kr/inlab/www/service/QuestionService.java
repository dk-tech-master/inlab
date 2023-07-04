package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateQuestionDto;

public interface QuestionService {
	void createQuestion(RequestCreateQuestionDto requestDto);

	//getQuestion();
}
