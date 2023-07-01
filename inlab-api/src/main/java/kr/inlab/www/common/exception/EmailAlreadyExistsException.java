package kr.inlab.www.common.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public static final String MESSAGE = "이미 사용중인 이메일입니다.";

    public EmailAlreadyExistsException() {
        super(MESSAGE);
    }
}
