package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class GptCommentNotFoundException extends InlabException {

    private static final String MESSAGE = "존재하지 않는 GPT 코멘트입니다.";

    public GptCommentNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
