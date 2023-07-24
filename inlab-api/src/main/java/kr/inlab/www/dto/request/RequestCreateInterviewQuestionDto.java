package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateInterviewQuestionDto {

    private Long interviewId;

    private Long questionId;

    private Long questionVersionId;

    @Builder
    public RequestCreateInterviewQuestionDto(Long interviewId, Long questionId, Long questionVersionId) {
        this.interviewId = interviewId;
        this.questionId = questionId;
        this.questionVersionId = questionVersionId;
    }
}
