package kr.inlab.www.service;

import java.io.IOException;
import javax.mail.MessagingException;
import kr.inlab.www.dto.request.RequestCheckVerificationCode;

public interface EmailService {

    void sendEmail(String email, String verificationCode) throws MessagingException, IOException;
}
