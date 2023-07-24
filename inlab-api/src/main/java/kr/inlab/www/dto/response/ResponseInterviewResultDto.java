package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewResultDto {

    private String interviewTitle;

    private String intervieweeName;

    private List<ResponseInterviewQuestionResultDto> responseInterviewQuestionResultDtoList;

    @Builder
    public ResponseInterviewResultDto(String interviewTitle, String intervieweeName, List<ResponseInterviewQuestionResultDto> responseInterviewQuestionResultDtoList) {
        this.interviewTitle = interviewTitle;
        this.intervieweeName = intervieweeName;
        this.responseInterviewQuestionResultDtoList = responseInterviewQuestionResultDtoList;
    }
}
