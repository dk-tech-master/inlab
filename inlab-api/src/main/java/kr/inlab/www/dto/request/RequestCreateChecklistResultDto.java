package kr.inlab.www.dto.request;

import kr.inlab.www.common.type.YesNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCreateChecklistResultDto {

    private YesNo isChecked;

    private Long checklistId;

    @Builder
    public RequestCreateChecklistResultDto(YesNo isChecked, Long checklistId) {
        this.isChecked = isChecked;
        this.checklistId = checklistId;
    }
}
