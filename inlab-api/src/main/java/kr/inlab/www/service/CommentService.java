package kr.inlab.www.service;

import kr.inlab.www.entity.InterviewQuestionResult;

public interface CommentService {

    void createComment(InterviewQuestionResult interviewQuestionResult, String content);
}
