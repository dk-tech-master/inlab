package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewQuestionDto {

//    private Long interviewQuestionId;
//    private String questionTitle;
//    private String questionLevelName;
//    private String positionName;
//    private String questionTypeName;
//
//    @Builder
//    public ResponseInterviewQuestionDto(Long interviewQuestionId, String questionTitle, String questionLevelName,
//                                        String positionName, String questionTypeName) {
//        this.interviewQuestionId = interviewQuestionId;
//        this.questionTitle = questionTitle;
//        this.questionLevelName = questionLevelName;
//        this.positionName = positionName;
//        this.questionTypeName = questionTypeName;
//    }

    private Long interviewQuestionId;
    private String questionTitle;
    private String questionLevelName;
    private String positionName;
    private String questionTypeName;
    private Integer version;

    @Builder
    public ResponseInterviewQuestionDto(Long interviewQuestionId, String questionTitle, String questionLevelName,
                                        String positionName, String questionTypeName, Integer version) {
        this.interviewQuestionId = interviewQuestionId;
        this.questionTitle = questionTitle;
        this.questionLevelName = questionLevelName;
        this.positionName = positionName;
        this.questionTypeName = questionTypeName;
        this.version = version;
    }
}
