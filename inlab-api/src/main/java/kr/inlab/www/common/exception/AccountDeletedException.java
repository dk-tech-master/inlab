package kr.inlab.www.common.exception;

import org.springframework.security.core.AuthenticationException;

public class AccountDeletedException extends AuthenticationException {
    public static final String MESSAGE = "탈퇴된 계정입니다.";

    public AccountDeletedException() {
        super(MESSAGE);
    }
}
