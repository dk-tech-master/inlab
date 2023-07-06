package kr.inlab.www.entity;

import kr.inlab.www.dto.response.ResponseCommentDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_question_result_id")
    private InterviewQuestionResult interviewQuestionResult;

    @Builder
    public Comment(String content, InterviewQuestionResult interviewQuestionResult) {
        this.content = content;
        this.interviewQuestionResult = interviewQuestionResult;
    }

    public ResponseCommentDto toResponseCommentDto() {
        return ResponseCommentDto.builder()
                .commentId(this.commentId)
                .content(this.content)
                .createdAt(this.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
