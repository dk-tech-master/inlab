package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewAnswerNotFoundException extends InlabException {

    private static final String MESSAGE = "존재하지 않는 면접 답변입니다.";

    public InterviewAnswerNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
