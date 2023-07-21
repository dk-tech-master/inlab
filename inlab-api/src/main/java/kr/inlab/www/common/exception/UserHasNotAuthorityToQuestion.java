package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class UserHasNotAuthorityToQuestion extends InlabException {

    public static final String MESSAGE = "질문에 접근권한이 없습니다.";

    public UserHasNotAuthorityToQuestion() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {return HttpStatus.UNAUTHORIZED.value();}
}
