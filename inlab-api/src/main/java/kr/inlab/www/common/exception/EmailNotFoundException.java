package kr.inlab.www.common.exception;

public class EmailNotFoundException extends RuntimeException {
    public static final String MESSAGE = "해당 이메일을 찾을 수 없습니다.";

    public EmailNotFoundException() {
        super(MESSAGE);
    }
}
