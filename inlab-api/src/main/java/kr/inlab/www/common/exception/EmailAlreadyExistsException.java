package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends InlabException {
    public static final String MESSAGE = "이미 사용중인 이메일입니다.";

    public EmailAlreadyExistsException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
