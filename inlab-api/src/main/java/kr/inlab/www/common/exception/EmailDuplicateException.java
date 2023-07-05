package kr.inlab.www.common.exception;

public class EmailDuplicateException extends RuntimeException {

    public static final String MESSAGE = "Email 중복 예외";

    public EmailDuplicateException() {
        super(MESSAGE);
    }
}
