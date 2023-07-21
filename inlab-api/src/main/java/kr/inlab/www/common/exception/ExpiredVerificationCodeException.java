package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class ExpiredVerificationCodeException extends InlabException {
    public static final String MESSAGE = "인증 시간이 만료되었습니다. 다시 인증을 진행해주세요.";

    public ExpiredVerificationCodeException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {return HttpStatus.UNAUTHORIZED.value();}
}
