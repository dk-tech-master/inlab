package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

//todo Runtime 과 Throwable 중 뭘 던지지?
public class UserNotFoundException extends InlabException {

    public static final String MESSAGE = "사용자 정보를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
