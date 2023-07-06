package kr.inlab.www.service;

import kr.inlab.www.dto.response.ResponseInterviewAnswerDto;
import kr.inlab.www.entity.InterviewAnswer;
import kr.inlab.www.entity.InterviewQuestionResult;

public interface InterviewAnswerService {

    InterviewAnswer createInterviewAnswer(InterviewQuestionResult interviewQuestionResult, String content);

    ResponseInterviewAnswerDto getInterviewAnswer(InterviewQuestionResult interviewQuestionResult);
}
