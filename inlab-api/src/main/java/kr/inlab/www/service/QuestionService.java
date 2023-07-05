package kr.inlab.www.service;

import java.util.List;
import java.util.Optional;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateQuestionDto;
import kr.inlab.www.dto.request.RequestGetQuestionsDto;
import kr.inlab.www.dto.response.ResponseGetQuestionDto;
import kr.inlab.www.dto.response.ResponseGetQuestionsDto;

public interface QuestionService {
	void createQuestion(RequestCreateQuestionDto requestDto);

	ResponseGetQuestionDto getQuestion(Long questionId);

	ResponseListDto<ResponseGetQuestionsDto> getQuestions(RequestGetQuestionsDto requestDto,
		Integer positionId,
		Integer questionTypeId,
		Integer questionLevelId,
		String titleKeyword);
}
