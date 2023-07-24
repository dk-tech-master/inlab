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
public class ResponseGetUserDto {

    private Long userId;

    private LocalDateTime createdAt;

    private String nickname;

    private String email;

    private Boolean isVerified;

    @Builder
    public ResponseGetUserDto(Long userId, LocalDateTime createdAt, String nickname, String email,
        Boolean isVerified) {
        this.userId = userId;
        this.createdAt = createdAt;
        this.nickname = nickname;
        this.email = email;
        this.isVerified = isVerified;
    }
}
