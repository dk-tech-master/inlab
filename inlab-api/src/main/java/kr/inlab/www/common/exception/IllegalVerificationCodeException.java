package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class IllegalVerificationCodeException extends InlabException {
    public static final String MESSAGE = "인증번호가 일치 하지 않습니다.";

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    public IllegalVerificationCodeException() {
        super(MESSAGE);
    }
}
