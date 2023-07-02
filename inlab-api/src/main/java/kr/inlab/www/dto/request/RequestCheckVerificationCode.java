package kr.inlab.www.dto.request;

import lombok.Getter;

@Getter
public class RequestCheckVerificationCode {

    private String email;
    private String verificationCode;
}
