package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewQuestionDto {

    private Long interviewQuestionId;
    private Integer levelId;
    private String levelName;
    private String questionTitle;
    private String questionLevelName;
    private String positionName;
    private String questionTypeName;
    private Integer version;

    @Builder
    public ResponseInterviewQuestionDto(Long interviewQuestionId, Integer levelId, String levelName,
                                        String questionTitle, String questionLevelName, String positionName, String questionTypeName, Integer version) {
        this.interviewQuestionId = interviewQuestionId;
        this.levelId = levelId;
        this.levelName = levelName;
        this.questionTitle = questionTitle;
        this.questionLevelName = questionLevelName;
        this.positionName = positionName;
        this.questionTypeName = questionTypeName;
        this.version = version;
    }
}
