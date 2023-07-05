package kr.inlab.www.entity;

import kr.inlab.www.common.type.YesNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChecklistResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistResultId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(1) default 'N'")
    private YesNo isChecked;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_question_id")
    private InterviewQuestion interviewQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_result_id")
    private InterviewResult interviewResult;

    @Builder
    public ChecklistResult(YesNo isChecked, Checklist checklist, InterviewQuestion interviewQuestion, InterviewResult interviewResult) {
        this.isChecked = isChecked;
        this.checklist = checklist;
        this.interviewQuestion = interviewQuestion;
        this.interviewResult = interviewResult;
    }
}
