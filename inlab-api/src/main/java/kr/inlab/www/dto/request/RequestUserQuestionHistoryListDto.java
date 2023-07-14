package kr.inlab.www.dto.request;

import kr.inlab.www.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RequestUserQuestionHistoryListDto extends RequestListDto {

    private String keyword;

    @Builder
    public RequestUserQuestionHistoryListDto(String keyword) {
        this.keyword = keyword;
    }
}
