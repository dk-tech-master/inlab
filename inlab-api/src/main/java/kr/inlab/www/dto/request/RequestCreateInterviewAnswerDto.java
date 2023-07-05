package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateInterviewAnswerDto {

    private String content;

    private Long interviewQuestionId;

    @Builder
    public RequestCreateInterviewAnswerDto(String content, Long interviewQuestionId) {
        this.content = content;
        this.interviewQuestionId = interviewQuestionId;
    }
}
