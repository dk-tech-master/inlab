package kr.inlab.www.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestPositionDto {

    private String positionName;

    @Builder
    public RequestPositionDto(String positionName) {

        this.positionName = positionName;
    }

}
