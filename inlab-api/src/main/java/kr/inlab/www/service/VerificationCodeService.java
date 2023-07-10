package kr.inlab.www.service;

import kr.inlab.www.common.exception.ExpiredVerificationCodeException;
import kr.inlab.www.dto.request.RequestCheckVerificationCode;

public interface VerificationCodeService {

    String createVerificationCode(String email);

    String createVerificationCodeForUpdate(String email);

    boolean checkVerificationCode(RequestCheckVerificationCode request) throws ExpiredVerificationCodeException;
}
