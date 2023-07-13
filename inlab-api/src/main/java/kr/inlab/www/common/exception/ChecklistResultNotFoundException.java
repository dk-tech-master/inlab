package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class ChecklistResultNotFoundException extends InlabException{

    public static final String MESSAGE = "존재하지 않는 체크리스트 결과입니다";

    public ChecklistResultNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
