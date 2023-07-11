package kr.inlab.www.dto.response;

import kr.inlab.www.dto.common.ChecklistDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewQuestionnaireDto {

    private Long InterviewQuestionId;

    private String questionTitle;

    List<ChecklistDto> checklistDtoList;

    public ResponseInterviewQuestionnaireDto(Long interviewQuestionId, String questionTitle, List<ChecklistDto> checklistDtoList) {
        InterviewQuestionId = interviewQuestionId;
        this.questionTitle = questionTitle;
        this.checklistDtoList = checklistDtoList;
    }
}
