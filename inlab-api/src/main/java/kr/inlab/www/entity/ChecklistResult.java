package kr.inlab.www.entity;

import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.dto.response.ResponseChecklistResultDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_question_result_id")
    private InterviewQuestionResult interviewQuestionResult;

    @Builder
    public ChecklistResult(YesNo isChecked, Checklist checklist, InterviewQuestionResult interviewQuestionResult) {
        this.isChecked = isChecked;
        this.checklist = checklist;
        this.interviewQuestionResult = interviewQuestionResult;
    }

    public void editChecklistResult(YesNo yesNo) {
        this.isChecked = yesNo;
    }

    public ResponseChecklistResultDto toResponseChecklistResultDto() {
        return ResponseChecklistResultDto.builder()
                .checklistResultId(this.checklistResultId)
                .isChecked(this.getIsChecked())
                .build();
    }
}
