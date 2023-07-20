package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class AdminCouldNotDeleteException extends InlabException {

    public static final String MESSAGE = "관리자 계정은 삭제할 수 없습니다.";

    public AdminCouldNotDeleteException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
