package kr.inlab.www.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import kr.inlab.www.common.exception.EmailAlreadyExistsException;
import kr.inlab.www.common.exception.EmailNotFoundException;
import kr.inlab.www.common.exception.ExpiredVerificationCodeException;
import kr.inlab.www.dto.request.RequestCheckVerificationCode;
import kr.inlab.www.entity.VerificationCode;
import kr.inlab.www.repository.UserRepository;
import kr.inlab.www.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    public static final long VERIFICATION_CODE_VALIDITY_MINUTE = 3;

    @Override
    @Transactional
    public String createVerificationCode(String email) {
        if (userRepository.findByEmail(email).isPresent()){
            throw new EmailAlreadyExistsException();
        }
        String randomCode = UUID.randomUUID().toString();
        VerificationCode verificationCode = VerificationCode.builder()
            .email(email)
            .code(randomCode)
            .expiredAt(LocalDateTime.now().plusMinutes(VERIFICATION_CODE_VALIDITY_MINUTE))
            .build();
        Optional<VerificationCode> verificationCodeByEmail = verificationRepository.findByEmail(email);

        if(verificationCodeByEmail.isEmpty()){
            verificationRepository.save(verificationCode);
        } else {
            verificationRepository.delete(verificationCodeByEmail.get());
            verificationRepository.save(verificationCode);
        }

        return randomCode;
    }

    @Override
    public boolean checkVerificationCode(RequestCheckVerificationCode request) throws ExpiredVerificationCodeException {
        VerificationCode verificationCode = verificationRepository.findByEmail(request.getEmail())
            .orElseThrow(EmailNotFoundException::new);
        checkExpiredVerificationCode(verificationCode);
        return verificationCode.getCode().equalsIgnoreCase(request.getVerificationCode());
    }

    private void checkExpiredVerificationCode(VerificationCode verificationCode)
        throws ExpiredVerificationCodeException {
        if(verificationCode.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new ExpiredVerificationCodeException();
        }
    }
}
