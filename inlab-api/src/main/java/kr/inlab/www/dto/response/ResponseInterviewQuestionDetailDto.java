package kr.inlab.www.dto.response;

import kr.inlab.www.dto.common.ChecklistDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewQuestionDetailDto {

    private String questionTitle;

    private String questionTypeName;

    private String positionName;

    private String questionLevelName;

    private int version;

    private List<ChecklistDto> checklist;

    @Builder
    public ResponseInterviewQuestionDetailDto(String questionTitle, String questionTypeName, String positionName,
                                              String questionLevelName, int version, List<ChecklistDto> checklist) {

        this.questionTitle = questionTitle;
        this.questionTypeName = questionTypeName;
        this.positionName = positionName;
        this.questionLevelName = questionLevelName;
        this.version = version;
        this.checklist = checklist;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class InterviewQuestionDetailDto {

        private String questionTitle;

        private String questionTypeName;

        private String positionName;

        private String questionLevelName;

        private Long questionVersionId;

        private int version;

        public InterviewQuestionDetailDto(String questionTitle, String questionTypeName, String positionName,
                                          String questionLevelName, Long questionVersionId, int version) {
            this.questionTitle = questionTitle;
            this.questionTypeName = questionTypeName;
            this.positionName = positionName;
            this.questionLevelName = questionLevelName;
            this.questionVersionId = questionVersionId;
            this.version = version;
        }
    }
}
