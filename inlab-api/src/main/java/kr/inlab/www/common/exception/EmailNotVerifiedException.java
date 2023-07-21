package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class EmailNotVerifiedException extends InlabException {
    public static final String MESSAGE = "이메일 인증을 진행해주세요.";

    public EmailNotVerifiedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {return HttpStatus.UNAUTHORIZED.value();}
}
