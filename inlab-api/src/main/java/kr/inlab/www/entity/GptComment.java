package kr.inlab.www.entity;

import kr.inlab.www.dto.response.ResponseGptCommentIdDto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_question_result_id")
    private InterviewQuestionResult interviewQuestionResult;

    @Builder
    public GptComment(String requestContent, String responseContent, InterviewQuestionResult interviewQuestionResult) {
        this.requestContent = requestContent;
        this.responseContent = responseContent;
        this.interviewQuestionResult = interviewQuestionResult;
    }

    public ResponseGptCommentIdDto toResponseGptCommentIdDto() {
        return ResponseGptCommentIdDto.builder()
                .gptCommentId(this.gptCommentId)
                .build();
    }

    public void saveGptResponse(String gptResponse) {
        this.responseContent = gptResponse;
    }
}
