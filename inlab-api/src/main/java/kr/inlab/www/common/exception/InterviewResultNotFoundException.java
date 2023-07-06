package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewResultNotFoundException extends InlabException {

    private static final String MESSAGE = "존재하지 않는 인터뷰 결과입니다.";

    public InterviewResultNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
