package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class PositionDeleteNotAllowedException extends InlabException{

    private static final String MESSAGE = "직무 카테고리를 삭제할 수 없습니다. 연관된 질문이 존재합니다.";

    public PositionDeleteNotAllowedException() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
