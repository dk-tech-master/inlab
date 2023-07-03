package kr.inlab.www.entity;

import kr.inlab.www.common.type.YesNo;
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
public class QuestionVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionVersionId;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "tinyint default 1")
    private Integer version;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(1) default 'Y'")
    private YesNo isLatest;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    public QuestionVersion(String title, Integer version, Question question) {
        this.title = title;
        this.version = version;
        this.question = question;
    }
}
