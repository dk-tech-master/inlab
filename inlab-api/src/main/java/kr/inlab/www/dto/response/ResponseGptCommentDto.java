package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseGptCommentDto {

    private Long gptCommentId;

    private String requestContent;

    private String responseContent;

    @Builder
    public ResponseGptCommentDto(Long gptCommentId, String requestContent, String responseContent) {
        this.gptCommentId = gptCommentId;
        this.requestContent = requestContent;
        this.responseContent = responseContent;
    }
}
