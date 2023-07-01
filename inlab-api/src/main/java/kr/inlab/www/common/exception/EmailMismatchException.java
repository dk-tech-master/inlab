package kr.inlab.www.common.exception;

public class EmailMismatchException extends RuntimeException{
    public static final String MESSAGE = "인증에 사용된 이메일과 회원가입 양식의 이메일의 정보가 다릅니다.";

    public EmailMismatchException() {
        super(MESSAGE);
    }
}
