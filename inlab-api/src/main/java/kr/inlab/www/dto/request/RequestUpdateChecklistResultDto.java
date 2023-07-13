package kr.inlab.www.dto.request;

import kr.inlab.www.common.type.YesNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestUpdateChecklistResultDto {

    private List<ChecklistResultDto> checklistResultDtoList;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ChecklistResultDto {

        private Long checklistResultId;

        private YesNo isChecked;

        @Builder
        public ChecklistResultDto(Long checklistResultId, YesNo isChecked) {
            this.checklistResultId = checklistResultId;
            this.isChecked = isChecked;
        }
    }

    @Builder
    public RequestUpdateChecklistResultDto(List<ChecklistResultDto> checklistResultDtoList) {
        this.checklistResultDtoList = checklistResultDtoList;
    }
}
