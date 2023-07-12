package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseCommentDto {

    private Long commentId;

    private String content;

    private String createdAt;

    private String modifiedAt;

    @Builder
    public ResponseCommentDto(Long commentId, String content, String createdAt, String modifiedAt) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
