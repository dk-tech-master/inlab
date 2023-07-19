package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestUpdateInterviewQuestionResultDto;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;
import kr.inlab.www.entity.InterviewResult;

public interface InterviewQuestionResultService {

    InterviewQuestionResult createInterviewQuestionResult(InterviewQuestion interviewQuestion, InterviewResult interviewResult);

    void updateInterviewQuestionResult(RequestUpdateInterviewQuestionResultDto requestDto);
}
