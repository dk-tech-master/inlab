package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewQuestionResultDto {

    private String interviewQuestionTitle;

    private ResponseInterviewAnswerDto responseInterviewAnswerDto;

    private ResponseCommentDto responseCommentDto;

    private ResponseGptCommentIdDto responseGptCommentIdDto;

    private List<ResponseChecklistDto> responseChecklistDtoList;

    @Builder
    public ResponseInterviewQuestionResultDto(String interviewQuestionTitle, ResponseInterviewAnswerDto responseInterviewAnswerDto, ResponseCommentDto responseCommentDto, ResponseGptCommentIdDto responseGptCommentIdDto, List<ResponseChecklistDto> responseChecklistDtoList) {
        this.interviewQuestionTitle = interviewQuestionTitle;
        this.responseInterviewAnswerDto = responseInterviewAnswerDto;
        this.responseCommentDto = responseCommentDto;
        this.responseGptCommentIdDto = responseGptCommentIdDto;
        this.responseChecklistDtoList = responseChecklistDtoList;
    }
}
