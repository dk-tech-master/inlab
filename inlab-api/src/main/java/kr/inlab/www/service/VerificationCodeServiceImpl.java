package kr.inlab.www.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import kr.inlab.www.common.exception.EmailAlreadyExistsException;
import kr.inlab.www.common.exception.EmailNotFoundException;
import kr.inlab.www.common.exception.ExpiredVerificationCodeException;
import kr.inlab.www.dto.request.RequestCheckVerificationCode;
import kr.inlab.www.entity.VerificationCode;
import kr.inlab.www.repository.UserRepository;
import kr.inlab.www.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
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
        checkVerificationAlreadyIssuedAndThenUpdate(email, verificationCode);

        return randomCode;
    }

    @Override
    @Transactional
    public String createVerificationCodeForUpdate(String email) {
        String randomCode = UUID.randomUUID().toString();
        VerificationCode verificationCode = VerificationCode.builder()
            .email(email)
            .code(randomCode)
            .expiredAt(LocalDateTime.now().plusMinutes(VERIFICATION_CODE_VALIDITY_MINUTE))
            .build();
        checkVerificationAlreadyIssuedAndThenUpdate(email, verificationCode);

        return randomCode;
    }

    private void checkVerificationAlreadyIssuedAndThenUpdate(String email, VerificationCode verificationCode) {
        Optional<VerificationCode> verificationCodeByEmail = verificationCodeRepository.findByEmail(email);

        if(verificationCodeByEmail.isEmpty()){
            verificationCodeRepository.save(verificationCode);
        } else {
            verificationCodeRepository.delete(verificationCodeByEmail.get());
            verificationCodeRepository.save(verificationCode);
        }
    }

    @Override
    public boolean checkVerificationCode(RequestCheckVerificationCode request) throws ExpiredVerificationCodeException {
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(request.getEmail())
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
