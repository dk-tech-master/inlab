package kr.inlab.www.common.exception;

public class AdminCouldNotDeleteException extends RuntimeException {

    public static final String MESSAGE = "관리자 계정은 삭제할 수 없습니다.";

    public AdminCouldNotDeleteException() {
        super(MESSAGE);
    }
}
