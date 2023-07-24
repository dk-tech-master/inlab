package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseUserQuestionHistoryDto {

    private Long questionHistoryId;

    private String questionTitle;

    private String questionVersion;

    private String nickname;

    private String readingTime;

    @Builder
    public ResponseUserQuestionHistoryDto(Long questionHistoryId, String questionTitle, String questionVersion, String nickname, String readingTime) {
        this.questionHistoryId = questionHistoryId;
        this.questionTitle = questionTitle;
        this.questionVersion = questionVersion;
        this.nickname = nickname;
        this.readingTime = readingTime;
    }
}
