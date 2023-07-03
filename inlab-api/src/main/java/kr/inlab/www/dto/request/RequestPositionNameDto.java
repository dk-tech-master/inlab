package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED )
public class RequestPositionNameDto {

    private String positionName;

    @Builder
    public RequestPositionNameDto(String positionName) {
        this.positionName = positionName;
    }
}
