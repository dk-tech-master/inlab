package kr.inlab.www.dto.request;

import kr.inlab.www.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RequestInterviewResultListDto extends RequestListDto {

    private String intervieweeName;

    private String startDate;

    private String endDate;

    @Builder
    public RequestInterviewResultListDto(String intervieweeName, String startDate, String endDate) {
        this.intervieweeName = intervieweeName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
