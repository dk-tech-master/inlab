package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateInterviewDto {

    private Long userId;
    private String interviewTitle;

    @Builder
    public RequestCreateInterviewDto(Long userId, String interviewTitle) {
        this.userId = userId;
        this.interviewTitle = interviewTitle;
    }
}
