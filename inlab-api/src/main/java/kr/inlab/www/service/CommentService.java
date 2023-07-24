package kr.inlab.www.service;

import kr.inlab.www.dto.request.RequestUpdateCommentDto;
import kr.inlab.www.dto.response.ResponseCommentDto;
import kr.inlab.www.entity.InterviewQuestionResult;

public interface CommentService {

    void createComment(InterviewQuestionResult interviewQuestionResult, String content);

    ResponseCommentDto getComment(InterviewQuestionResult interviewQuestionResult);

    void updateComment(Long commentId, RequestUpdateCommentDto requestDto);
}
