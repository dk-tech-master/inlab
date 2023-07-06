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

    @Builder
    public ResponseCommentDto(Long commentId, String content, String createdAt) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
