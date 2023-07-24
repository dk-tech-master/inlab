package kr.inlab.www.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseGetInterviewDto {

    private Long interviewId;
    private String interviewerNickname;
    private String interviewTitle;
    private Integer questionCount;
    private LocalDateTime createdAt;

    @Builder
    public ResponseGetInterviewDto(Long interviewId, String interviewerNickname, String interviewTitle,
        Integer questionCount, LocalDateTime createdAt) {
        this.interviewId = interviewId;
        this.interviewerNickname = interviewerNickname;
        this.interviewTitle = interviewTitle;
        this.questionCount = questionCount;
        this.createdAt = createdAt;
    }
}
