package kr.inlab.www.service;

import kr.inlab.www.entity.Comment;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewResult;

public interface CommentService {

    Comment getComment(Long commentId);

    void createComment(InterviewQuestion interviewQuestion, InterviewResult interviewResult, String content);
}
