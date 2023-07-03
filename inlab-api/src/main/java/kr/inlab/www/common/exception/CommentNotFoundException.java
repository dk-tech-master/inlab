package kr.inlab.www.common.exception;

public class CommentNotFoundException extends InlabException {

    private static final String MESSAGE = "존재하지 않는 코멘트입니다.";

    public CommentNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
