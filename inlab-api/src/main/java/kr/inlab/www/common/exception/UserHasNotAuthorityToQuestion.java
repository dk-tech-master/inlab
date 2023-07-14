package kr.inlab.www.common.exception;

public class UserHasNotAuthorityToQuestion extends RuntimeException {

    public static final String MESSAGE = "질문에 접근권한이 없습니다.";

    public UserHasNotAuthorityToQuestion() {
        super(MESSAGE);
    }
}
