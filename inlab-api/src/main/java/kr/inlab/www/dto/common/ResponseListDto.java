package kr.inlab.www.dto.common;

import java.util.List;
import kr.inlab.www.common.util.PagingUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseListDto<T> {

    List<T> responseList;

    private PagingUtil pagingUtil;

    @Builder
    public ResponseListDto(List<T> responseList, PagingUtil pagingUtil) {
        this.responseList = responseList;
        this.pagingUtil = pagingUtil;
    }
}
