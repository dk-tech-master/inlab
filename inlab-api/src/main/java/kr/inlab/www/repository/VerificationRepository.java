package kr.inlab.www.repository;

import java.util.Optional;
import kr.inlab.www.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByEmail(String email);
}
