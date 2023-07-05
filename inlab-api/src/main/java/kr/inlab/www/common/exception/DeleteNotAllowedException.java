package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class DeleteNotAllowedException extends InlabException{

    private static final String MESSAGE = "와 연관된 질문이 있어 삭제할 수 없습니다.";

    public DeleteNotAllowedException(String name) {
        super(name + MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.value();
    }
}
