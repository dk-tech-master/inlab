package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseInterviewAnswerDto {

    private String content;

    private String createdAt;

    @Builder
    public ResponseInterviewAnswerDto(String content, String createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }
}
