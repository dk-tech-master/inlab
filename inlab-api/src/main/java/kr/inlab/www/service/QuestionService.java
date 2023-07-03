package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestQuestionDto;

public interface QuestionService {
	void createQuestion(RequestQuestionDto requestDto);
}
