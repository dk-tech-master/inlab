package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class QuestionVersionNotFoundException extends InlabException {
	private static final String MESSAGE = "존재하지 않는 질문 버전입니다.";

	public QuestionVersionNotFoundException() {
		super(MESSAGE);
	}
	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}
}
