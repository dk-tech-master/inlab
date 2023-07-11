package kr.inlab.www.entity;

import kr.inlab.www.common.type.YesNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
public class QuestionVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionVersionId;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "tinyint default 1")
    private Integer version;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(1) default 'Y'")
    private YesNo isLatest;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_level_id")
    private QuestionLevel questionLevel;

    @OneToMany(mappedBy = "questionVersion", fetch = FetchType.LAZY)
    private List<Checklist> checklistList;

    @Builder
    public QuestionVersion(String title, Integer version, Question question, QuestionLevel questionLevel) {
        this.title = title;
        this.version = version;
        this.question = question;
        this.questionLevel = questionLevel;
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateVersion(Integer newVersion) {
        this.version = newVersion;
    }

    public void updateQuestion(Question newQuestion) {
        this.question = newQuestion;
    }

    public void updateQuestionLevel(QuestionLevel newQuestionLevel) {
        this.questionLevel = newQuestionLevel;
    }

	public void updateIsLatest(YesNo newStatus) {
        this.isLatest = newStatus;
	}
}
