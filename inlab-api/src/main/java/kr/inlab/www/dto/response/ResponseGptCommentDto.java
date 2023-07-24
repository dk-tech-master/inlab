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

    private String createdAt;

    private String modifiedAt;

    @Builder
    public ResponseGptCommentDto(Long gptCommentId, String requestContent, String responseContent, String createdAt, String modifiedAt) {
        this.gptCommentId = gptCommentId;
        this.requestContent = requestContent;
        this.responseContent = responseContent;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
