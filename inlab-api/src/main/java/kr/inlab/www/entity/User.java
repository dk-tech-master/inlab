package kr.inlab.www.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import kr.inlab.www.common.type.RoleType;
import kr.inlab.www.common.type.UserStatus;
import kr.inlab.www.dto.response.ResponseGetUserDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @NotNull
    @Column(nullable = false, length = 500)
    private String password;

    @CreatedDate
    private LocalDateTime passwordModifiedAt;

    @Column(columnDefinition = "tinyint default 0")
    private Integer loginAttempt;

    @Column(columnDefinition = "varchar(255) default 'ACTIVE'")
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @CreatedDate
    private LocalDateTime loginBlockUntil;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "user")
    private List<Role> roles = new ArrayList<>();

    @Builder
    public User(Long userId, String email, String nickname, String password) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void incrementLoginAttempt() {
        this.loginAttempt++;
    }

    public void resetLoginAttempt() {
        this.loginAttempt = 0;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
        this.passwordModifiedAt = LocalDateTime.now();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateUserStatusDelete() {
        this.userStatus = UserStatus.DELETE;
    }

    public void updateLoginBlockUntil(){
        this.loginBlockUntil = LocalDateTime.now().plusMinutes(30);
    }

    public ResponseGetUserDto toResponseGetUserDto() {
        return ResponseGetUserDto.builder()
            .userId(this.userId)
            .createdAt(this.createdAt)
            .nickname(this.nickname)
            .email(this.email)
            .isVerified(this.roles.stream().map(Role::getRoleType)
                .noneMatch(roleType -> roleType.equals(RoleType.ROLE_GUEST)))
            .build();
    }
}