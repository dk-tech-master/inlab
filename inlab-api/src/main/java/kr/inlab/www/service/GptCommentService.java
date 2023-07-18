package kr.inlab.www.service;

import kr.inlab.www.dto.response.ResponseGptCommentDto;
import kr.inlab.www.dto.response.ResponseGptCommentIdDto;
import kr.inlab.www.entity.InterviewQuestion;
import kr.inlab.www.entity.InterviewQuestionResult;

public interface GptCommentService {

    void createGptComment(InterviewQuestion interviewQuestion, InterviewQuestionResult interviewResult, String interviewAnswerContent);

    ResponseGptCommentDto getInterviewResultGptComment(InterviewQuestionResult interviewQuestionResult);

    ResponseGptCommentDto getGptComment(Long gptCommentId);
}
