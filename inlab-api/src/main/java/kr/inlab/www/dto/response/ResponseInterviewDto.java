package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ResponseInterviewDto {

    private Long interviewId;
    private String nickname;
    private String interviewTitle;
    private Long questionCount;

    public ResponseInterviewDto(Long interviewId, String interviewTitle, String nickname,Long questionCount) {
        this.interviewId = interviewId;
        this.nickname = nickname;
        this.interviewTitle = interviewTitle;
        this.questionCount = questionCount;
    }
}
