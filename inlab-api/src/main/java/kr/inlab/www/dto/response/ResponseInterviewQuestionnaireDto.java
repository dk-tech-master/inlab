package kr.inlab.www.dto.response;

import kr.inlab.www.dto.common.ChecklistDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewQuestionnaireDto {

    private String interviewTitle;

    private Long interviewQuestionId;

    private String questionTitle;

    private int version;

    private List<ChecklistDto> checklistDtoList;

    public ResponseInterviewQuestionnaireDto(String interviewTitle, Long interviewQuestionId, String questionTitle, int version, List<ChecklistDto> checklistDtoList) {
        this.interviewTitle = interviewTitle;
        this.interviewQuestionId = interviewQuestionId;
        this.questionTitle = questionTitle;
        this.version = version;
        this.checklistDtoList = checklistDtoList;
    }
}
