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

    private List<checklistResultDto> checklistResultDtoList;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class checklistResultDto {

        private Long checklistId;

        private YesNo isChecked;

        @Builder
        public checklistResultDto(Long checklistId, YesNo isChecked) {
            this.checklistId = checklistId;
            this.isChecked = isChecked;
        }
    }

    @Builder
    public RequestUpdateChecklistResultDto(List<checklistResultDto> checklistResultDtoList) {
        this.checklistResultDtoList = checklistResultDtoList;
    }
}
