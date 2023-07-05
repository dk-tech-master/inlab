package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewNotFoundException extends InlabException {

    private static final String MESSAGE = "존재하지 않는 면접입니다.";

    public InterviewNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
