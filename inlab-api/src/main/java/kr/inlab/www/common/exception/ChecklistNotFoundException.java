package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class ChecklistNotFoundException extends InlabException {

    private static final String MESSAGE = "존재하지 않는 체크리스트입니다.";

    public ChecklistNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
