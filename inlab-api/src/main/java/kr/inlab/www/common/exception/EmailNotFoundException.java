package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends InlabException {
    public static final String MESSAGE = "해당 이메일을 찾을 수 없습니다.";

    public EmailNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
