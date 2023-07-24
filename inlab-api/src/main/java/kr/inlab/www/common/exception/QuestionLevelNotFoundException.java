package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class QuestionLevelNotFoundException extends InlabException {

	private static final String MESSAGE = "존재하지 않는 난이도입니다.";

	public QuestionLevelNotFoundException() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}
}
