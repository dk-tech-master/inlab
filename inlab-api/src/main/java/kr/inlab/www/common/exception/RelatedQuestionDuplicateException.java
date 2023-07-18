package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class RelatedQuestionDuplicateException extends InlabException {

	private static final String MESSAGE = "이미 존재하는 꼬리질문입니다.";

	public RelatedQuestionDuplicateException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}
}
