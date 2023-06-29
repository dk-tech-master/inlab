package kr.inlab.www.entity;

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
public class GptComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gptCommentId;

    @NotNull
    private String requestContent;

    private String responseContent;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_question_id")
    private InterviewQuestion interviewQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_result_id")
    private InterviewResult interviewResult;

    @Builder
    public GptComment(String requestContent, String responseContent, InterviewQuestion interviewQuestion, InterviewResult interviewResult) {
        this.requestContent = requestContent;
        this.responseContent = responseContent;
        this.interviewQuestion = interviewQuestion;
        this.interviewResult = interviewResult;
    }
}
