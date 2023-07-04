package kr.inlab.www.dto.response;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter
@NoArgsConstructor
public class ResponseUserDto {

    private Long userId;

    private LocalDateTime createAt;

    private String nickname;

    private String email;

    private Boolean isVerified;

    @Builder
    public ResponseUserDto(Long userId, LocalDateTime createAt, String nickname, String email,
        Boolean isVerified) {
        this.userId = userId;
        this.createAt = createAt;
        this.nickname = nickname;
        this.email = email;
        this.isVerified = isVerified;
    }
}
