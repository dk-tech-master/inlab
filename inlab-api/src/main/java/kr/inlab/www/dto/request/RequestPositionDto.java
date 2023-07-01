package kr.inlab.www.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestPositionDto {

    private String positionName;
    private int page;

    @Builder
    public RequestPositionDto(String positionName, int page) {

        this.positionName = positionName;
        this.page = page;
    }

}
