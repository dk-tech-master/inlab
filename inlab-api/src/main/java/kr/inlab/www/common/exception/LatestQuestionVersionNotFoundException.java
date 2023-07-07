package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class LatestQuestionVersionNotFoundException extends InlabException {

	private static final String MESSAGE = "최신버전의 질문이 없습니다";

	public LatestQuestionVersionNotFoundException() {
		super(MESSAGE);
	}
	@Override
	public int getStatusCode() { return HttpStatus.NOT_FOUND.value(); }
}
