package kr.inlab.www.dto.response;

import kr.inlab.www.common.type.YesNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseChecklistDto {

    private String content;

    private YesNo isChecked;

    @Builder
    public ResponseChecklistDto(String content, YesNo isChecked) {
        this.content = content;
        this.isChecked = isChecked;
    }
}
