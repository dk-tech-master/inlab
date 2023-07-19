package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestUpdateInterviewQuestionResultDto {

    private RequestUpdateCommentDto commentDto;

    private List<RequestUpdateChecklistResultDto> checklistResultDtoList;

    @Builder
    public RequestUpdateInterviewQuestionResultDto(RequestUpdateCommentDto commentDto, List<RequestUpdateChecklistResultDto> checklistResultDtoList) {
        this.commentDto = commentDto;
        this.checklistResultDtoList = checklistResultDtoList;
    }
}
