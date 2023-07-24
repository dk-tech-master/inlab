package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class QuestionTypeNotFoundException extends InlabException {

    private static final String MESSAGE = "존재하지 않는 유형입니다.";

    public QuestionTypeNotFoundException() {
        super(MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
