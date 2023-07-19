package kr.inlab.www.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import kr.inlab.www.common.exception.ExpiredVerificationCodeException;
import kr.inlab.www.common.exception.IllegalVerificationCodeException;
import kr.inlab.www.dto.request.RequestCheckVerificationCode;
import kr.inlab.www.security.jwt.JwtTokenProvider;
import kr.inlab.www.service.EmailService;
import kr.inlab.www.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/verification_code")
@RequiredArgsConstructor
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @PostMapping
    public ResponseEntity createVerificationCode(@RequestParam String email) throws MessagingException, IOException {
        emailService.sendEmail(email, verificationCodeService.createVerificationCode(email));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity createVerificationCodeForUpdate(@RequestParam String email)
        throws MessagingException, IOException {
        emailService.sendEmail(email, verificationCodeService.createVerificationCodeForUpdate(email));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/check")
    public ResponseEntity checkVerificationCode(@RequestBody RequestCheckVerificationCode requestDto,
        HttpServletResponse response) throws ExpiredVerificationCodeException {
        if (verificationCodeService.checkVerificationCode(requestDto)) {
            Claims claims = Jwts.claims();

            jwtTokenProvider.putEmailToClaims(claims, requestDto.getEmail());

            Map<String, String> stringStringMap = jwtTokenProvider.generateEmailToken(claims);
            stringStringMap.forEach(response::addHeader);
        } else {
            throw new IllegalVerificationCodeException();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
