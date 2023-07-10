package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class InterviewTitleDuplicateException extends InlabException{

    private static final String MESSAGE = "중복되는 면접 제목이 있습니다.";

    public InterviewTitleDuplicateException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

}
