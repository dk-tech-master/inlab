package kr.inlab.www.dto.common;

import kr.inlab.www.common.util.PagingUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class ResponseListDto {

    private PagingUtil pagingUtil;

}
