package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewQuestionAlreadyExistsException extends InlabException{

    private static final String MESSAGE = "이미 등록된 질문입니다.";

    public InterviewQuestionAlreadyExistsException() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
