package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class QuestionTypeDeleteNotAllowedException extends InlabException{

    private static final String MESSAGE = "질문 유형 카테고리를 삭제할 수 없습니다. 연관된 질문이 존재합니다.";

    public QuestionTypeDeleteNotAllowedException() {
        super( MESSAGE);
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
