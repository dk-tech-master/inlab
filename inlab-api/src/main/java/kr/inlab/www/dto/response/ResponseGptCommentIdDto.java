package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseGptCommentIdDto {

    private Long gptCommentId;

    @Builder
    public ResponseGptCommentIdDto(Long gptCommentId) {
        this.gptCommentId = gptCommentId;
    }
}
