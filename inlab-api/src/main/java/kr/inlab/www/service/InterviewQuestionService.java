package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestCreateInterviewQuestionDto;
import kr.inlab.www.dto.response.ResponseInterviewQuestionDetailDto;
import kr.inlab.www.dto.response.ResponseInterviewQuestionDto;
import kr.inlab.www.entity.InterviewQuestion;

import java.util.List;

public interface InterviewQuestionService {

    InterviewQuestion getInterviewQuestion(Long interviewQuestionId);

    void createInterviewQuestion(RequestCreateInterviewQuestionDto requestDto);

    List<ResponseInterviewQuestionDto> getInterviewQuestionList(Long interviewId);

    void deleteInterviewQuestion(Long interviewQuestionId);

    ResponseInterviewQuestionDetailDto getInterviewQuestionDetail(Long interviewQuestionId);
}
