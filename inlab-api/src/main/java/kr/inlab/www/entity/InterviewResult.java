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
public class InterviewResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewResultId;

    @NotNull
    private String intervieweeName;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public InterviewResult(String intervieweeName) {
        this.intervieweeName = intervieweeName;
    }
}
