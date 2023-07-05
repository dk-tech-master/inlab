package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewQuestionNotFoundException extends InlabException {

    private static final String MESSAGE = "존재하지 않는 인터뷰 질문입니다.";

    public InterviewQuestionNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
