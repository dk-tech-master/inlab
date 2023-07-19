package kr.inlab.www.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestUpdateCommentDto {

    private Long commentId;

    private String content;

    @Builder
    public RequestUpdateCommentDto(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
