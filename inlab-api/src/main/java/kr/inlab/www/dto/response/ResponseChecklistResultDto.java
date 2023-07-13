package kr.inlab.www.dto.response;

import kr.inlab.www.common.type.YesNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseChecklistResultDto {

    private Long checklistResultId;

    private YesNo isChecked;

    @Builder
    public ResponseChecklistResultDto(Long checklistResultId, YesNo isChecked) {
        this.checklistResultId = checklistResultId;
        this.isChecked = isChecked;
    }
}
