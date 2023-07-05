package kr.inlab.www.service;

import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewResult;

public interface GptCommentService {

    void createGptComment(InterviewQuestion interviewQuestion, InterviewResult interviewResult, String interviewAnswerContent);
}
