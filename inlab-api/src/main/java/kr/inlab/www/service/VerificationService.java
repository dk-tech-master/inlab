package kr.inlab.www.service;

import kr.inlab.www.common.exception.ExpiredVerificationCodeException;
import kr.inlab.www.dto.request.RequestCheckVerificationCode;

public interface VerificationService {

    String createVerificationCode(String email);

    boolean checkVerificationCode(RequestCheckVerificationCode request) throws ExpiredVerificationCodeException;
}
