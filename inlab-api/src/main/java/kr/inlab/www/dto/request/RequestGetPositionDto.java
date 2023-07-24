package kr.inlab.www.dto.request;

import kr.inlab.www.dto.common.RequestListDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class RequestGetPositionDto extends RequestListDto {

    private String positionName;
}
