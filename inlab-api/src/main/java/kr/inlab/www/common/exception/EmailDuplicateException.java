package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class EmailDuplicateException extends InlabException {

    public static final String MESSAGE = "Email 중복 예외";

    public EmailDuplicateException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
