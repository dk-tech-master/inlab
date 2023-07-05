package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class PositionAlreadyExistsException extends InlabException {

    private static final String MESSAGE = "이미 생성된 직무 입니다.";

    public PositionAlreadyExistsException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
