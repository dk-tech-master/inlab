package kr.inlab.www.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationCodeId;

    @NotNull
    private String email;

    @NotNull
    private String code;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    @Builder
    public VerificationCode(String email, String code, LocalDateTime expiredAt) {
        this.email = email;
        this.code = code;
        this.expiredAt = expiredAt;
    }
}
