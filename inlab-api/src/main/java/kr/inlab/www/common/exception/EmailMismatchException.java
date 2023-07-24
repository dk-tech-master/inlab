package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class EmailMismatchException extends InlabException{
    public static final String MESSAGE = "인증에 사용된 이메일과 양식의 이메일의 정보가 다릅니다.";

    public EmailMismatchException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
