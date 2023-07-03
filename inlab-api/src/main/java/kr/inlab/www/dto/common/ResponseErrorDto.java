package kr.inlab.www.dto.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseErrorDto {

    private String code;

    private String message;

    @Builder
    public ResponseErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
