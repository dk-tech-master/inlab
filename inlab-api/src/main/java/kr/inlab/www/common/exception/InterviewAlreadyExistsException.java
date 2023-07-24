package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewAlreadyExistsException extends InlabException{

    private static final String MESSAGE = "이미 등록된 면접 입니다.";

    public InterviewAlreadyExistsException() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
