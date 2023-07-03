package kr.inlab.www.dto.response;

import kr.inlab.www.dto.common.ResponseListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePositionListDto extends ResponseListDto {

    List<ResponsePositionDto> positionList;

}
