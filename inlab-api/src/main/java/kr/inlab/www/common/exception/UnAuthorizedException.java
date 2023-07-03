package kr.inlab.www.common.exception;

public class UnAuthorizedException extends RuntimeException {

    public static final String MESSAGE = "권한이 없습니다.";

    public UnAuthorizedException() {
        super(MESSAGE);
    }
}
