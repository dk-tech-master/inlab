package kr.inlab.www.common.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginBlockedException extends AuthenticationException {

    public static final String MESSAGE = "로그인 실패로 인한 일정 시간 로그인 차단되었습니다.";

    public LoginBlockedException() {
        super(MESSAGE);
    }
}
