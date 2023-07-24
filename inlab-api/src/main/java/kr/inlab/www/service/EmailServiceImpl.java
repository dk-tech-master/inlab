package kr.inlab.www.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private static final String TITLE = "inlab 인증 메일 입니다.";
    private static final String TEMPLATE_FILE_PATH = "/static/email/email-template.html";

    public void sendEmail(String email, String verificationCode) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
        helper.setTo(email);
        helper.setSubject(TITLE);
        String htmlContent = getHtmlContentFromFile(verificationCode);
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    private String getHtmlContentFromFile(String verificationCode) throws IOException {
        String htmlContent;
        try (InputStream inputStream = getClass().getResourceAsStream(EmailServiceImpl.TEMPLATE_FILE_PATH)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                htmlContent = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
        return htmlContent.replace("{{verificationCode}}", verificationCode);
    }
}
