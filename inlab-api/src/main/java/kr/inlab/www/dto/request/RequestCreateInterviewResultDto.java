package kr.inlab.www.dto.request;

import kr.inlab.www.common.type.YesNo;
import kr.inlab.www.entity.InterviewResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateInterviewResultDto {

    private String intervieweeName;

    private Long interviewId;

    private List<InterviewResultDto> interviewResultDtos;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class InterviewResultDto {

        private Long interviewQuestionId;

        private CommentDto commentDto;

        private InterviewAnswerDto interviewAnswerDto;

        private List<ChecklistResultDto> checklistResultDtos;

        @Builder
        public InterviewResultDto(Long interviewQuestionId, CommentDto commentDto, InterviewAnswerDto interviewAnswerDto, List<ChecklistResultDto> checklistResultDtos) {
            this.interviewQuestionId = interviewQuestionId;
            this.commentDto = commentDto;
            this.interviewAnswerDto = interviewAnswerDto;
            this.checklistResultDtos = checklistResultDtos;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CommentDto {

        private String content;

        @Builder
        public CommentDto(String content) {
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class InterviewAnswerDto {

        private String content;

        @Builder
        public InterviewAnswerDto(String content) {
            this.content = content;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChecklistResultDto {

        private Long checklistId;

        private YesNo isChecked;

        @Builder
        public ChecklistResultDto(Long checklistId, YesNo isChecked) {
            this.checklistId = checklistId;
            this.isChecked = isChecked;
        }
    }

    @Builder
    public RequestCreateInterviewResultDto(String intervieweeName, Long interviewId, List<InterviewResultDto> interviewResultDtos) {
        this.intervieweeName = intervieweeName;
        this.interviewId = interviewId;
        this.interviewResultDtos = interviewResultDtos;
    }

    public InterviewResult toInterviewResultEntity() {
        return InterviewResult.builder()
                .intervieweeName(this.intervieweeName)
                .build();
    }
}
