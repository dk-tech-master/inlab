package kr.inlab.www.service;

import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;

public interface GptCommentService {

    void createGptComment(InterviewQuestion interviewQuestion, InterviewQuestionResult interviewResult, String interviewAnswerContent);
}
