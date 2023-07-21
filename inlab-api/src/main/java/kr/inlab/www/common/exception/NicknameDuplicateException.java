package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class NicknameDuplicateException extends InlabException {

    public static final String MESSAGE = "nickname 중복 에러.";

    public NicknameDuplicateException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
