package kr.inlab.www.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseAudioFileUrlDto {

    private String audioFileUrl;

    @Builder
    public ResponseAudioFileUrlDto(String audioFileUrl) {
        this.audioFileUrl = audioFileUrl;
    }
}
