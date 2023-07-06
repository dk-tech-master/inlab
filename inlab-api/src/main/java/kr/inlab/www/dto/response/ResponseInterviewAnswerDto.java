package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewAnswerDto {

    private Long interviewAnswerId;

    private String content;

    private String createdAt;

    @Builder
    public ResponseInterviewAnswerDto(Long interviewAnswerId, String content, String createdAt) {
        this.interviewAnswerId = interviewAnswerId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
