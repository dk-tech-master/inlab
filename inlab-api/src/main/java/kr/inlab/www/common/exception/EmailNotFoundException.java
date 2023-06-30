package kr.inlab.www.common.exception;

public class EmailNotFoundException extends RuntimeException {
    public static final String MESSAGE = "이메일 또는 비밀번호가 일치하지 않습니다.";

    public EmailNotFoundException() {
        super(MESSAGE);
    }
}
