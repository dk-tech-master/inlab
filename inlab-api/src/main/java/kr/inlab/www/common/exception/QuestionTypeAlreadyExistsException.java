package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class QuestionTypeAlreadyExistsException extends InlabException{

    private static final String MESSAGE = "이미 생성된 질문 유형입니다.";

    public QuestionTypeAlreadyExistsException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
