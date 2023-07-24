package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class PositionNotFoundException extends InlabException{

    private static final String MESSAGE = "존재하지 않는 직무입니다.";

    public PositionNotFoundException() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() { return HttpStatus.NOT_FOUND.value();}
}
