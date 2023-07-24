package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class PositionUpdateNotAllowedException extends InlabException{

    public static final String MESSAGE = "직무를 수정할 수 없습니다. 연관된 유형,질문이 존재합니다.";

    public PositionUpdateNotAllowedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
