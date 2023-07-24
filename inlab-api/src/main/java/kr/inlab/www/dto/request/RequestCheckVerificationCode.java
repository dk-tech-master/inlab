package kr.inlab.www.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCheckVerificationCode {

    private String email;
    private String verificationCode;
}
