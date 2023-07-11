package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewResultListDto {

    private Long interviewResultId;

    private String interviewTitle;

    private String intervieweeName;

    private String interviewerName;

    private String createdAt;

    @Builder
    public ResponseInterviewResultListDto(Long interviewResultId, String interviewTitle, String intervieweeName, String interviewerName, String createdAt) {
        this.interviewResultId = interviewResultId;
        this.interviewTitle = interviewTitle;
        this.intervieweeName = intervieweeName;
        this.interviewerName = interviewerName;
        this.createdAt = createdAt;
    }
}
