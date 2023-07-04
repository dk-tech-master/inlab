package kr.inlab.www.dto.request;

import kr.inlab.www.entity.InterviewResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateInterviewResultDto {

    private String intervieweeName;

    @Builder
    public RequestCreateInterviewResultDto(String intervieweeName) {

        this.intervieweeName = intervieweeName;
    }

    public InterviewResult toInterviewResultEntity() {
        return InterviewResult.builder()
                .intervieweeName(this.intervieweeName)
                .build();
    }
}
