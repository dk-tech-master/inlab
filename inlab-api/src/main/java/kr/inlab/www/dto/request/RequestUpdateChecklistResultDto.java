package kr.inlab.www.dto.request;

import kr.inlab.www.common.type.YesNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestUpdateChecklistResultDto {

    private Long checklistResultId;

    private YesNo isChecked;

    @Builder
    public RequestUpdateChecklistResultDto(Long checklistResultId, YesNo isChecked) {
        this.checklistResultId = checklistResultId;
        this.isChecked = isChecked;
    }
}
