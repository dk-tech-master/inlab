package kr.inlab.www.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * userStatus 가 Block 인 경우 발생하는 예외 클래스
 */
public class AccountBlockedException extends AuthenticationException {

    public static final String MESSAGE = "Account Blocked 입니뚱.";

    public AccountBlockedException() {
        super(MESSAGE);
    }
}
