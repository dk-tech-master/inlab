package kr.inlab.www.service;

import kr.inlab.www.entity.InterviewAnswer;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewResult;

public interface InterviewAnswerService {

    InterviewAnswer createInterviewAnswer(InterviewQuestion interviewQuestion, InterviewResult interviewResult, String content);
}
