package kr.inlab.www.dto.response;

import kr.inlab.www.dto.common.ChecklistDto;
import lombok.*;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewQuestionStartDto {

    private String interviewTitle;

    private Long interviewQuestionId;

    private Long questionVersionId;

    private String questionTitle;

    private int version;

    private List<ChecklistDto> checklistList;

    @Builder
    public ResponseInterviewQuestionStartDto(String interviewTitle, Long interviewQuestionId,
                                             Long questionVersionId, String questionTitle, int version, List<ChecklistDto> checklistList) {
        this.interviewTitle = interviewTitle;
        this.interviewQuestionId = interviewQuestionId;
        this.questionVersionId = questionVersionId;
        this.questionTitle = questionTitle;
        this.version = version;
        this.checklistList = checklistList;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class InterviewQuestionStartDto {

        private String interviewTitle;

        private Long interviewQuestionId;

        private Long questionVersionId;

        private String questionTitle;

        private int version;

        public InterviewQuestionStartDto(String interviewTitle, Long interviewQuestionId, Long questionVersionId, String questionTitle, int version) {
            this.interviewTitle = interviewTitle;
            this.interviewQuestionId = interviewQuestionId;
            this.questionVersionId = questionVersionId;
            this.questionTitle = questionTitle;
            this.version = version;
        }
    }
}
