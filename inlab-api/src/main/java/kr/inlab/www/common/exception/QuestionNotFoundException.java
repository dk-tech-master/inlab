package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class QuestionNotFoundException extends InlabException {

	private static final String MESSAGE = "존재하지 않는 질문입니다.";

	public QuestionNotFoundException() {
		super(MESSAGE);
	}
	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}
}
