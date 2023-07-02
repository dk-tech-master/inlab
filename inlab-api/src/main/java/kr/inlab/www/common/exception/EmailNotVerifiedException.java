package kr.inlab.www.common.exception;

public class EmailNotVerifiedException extends Throwable {
    public static final String MESSAGE = "이메일 인증을 진행해주세요.";

    public EmailNotVerifiedException() {
        super(MESSAGE);
    }
}
