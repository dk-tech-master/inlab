package kr.inlab.www.common.exception;

public class NicknameDuplicateException extends RuntimeException {

    public static final String MESSAGE = "nickname 중복 에러.";

    public NicknameDuplicateException() {
        super(MESSAGE);
    }
}
