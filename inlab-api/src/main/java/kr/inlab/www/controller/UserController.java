package kr.inlab.www.controller;

import javax.servlet.http.HttpServletRequest;
import kr.inlab.www.common.exception.EmailDuplicateException;
import kr.inlab.www.common.exception.EmailNotVerifiedException;
import kr.inlab.www.common.util.CreateHeaders;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.dto.request.RequestUpdateUserDto;
import kr.inlab.www.dto.request.RequestUsersDto;
import kr.inlab.www.dto.response.ResponseGetUserDto;
import kr.inlab.www.entity.User;
import kr.inlab.www.security.jwt.JwtTokenProvider;
import kr.inlab.www.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/health_check")
    public String healthCheck() {
        return "good_health";
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(userId));
    }

    /**
     * 원래 회원가입은 이메일 인증을 하고 jwt 토큰 까서 내가 가입시키려는 이메일이 유효한 이메일인지 확인하는데 개발할 때 간단하게 회원을 추가하기 위한 메서드 입니다.(추후 삭제 예정)
     */
    @PostMapping("/users/easy")
    public ResponseEntity createEasyUser(@RequestBody RequestCreateUserDto dto) throws EmailDuplicateException {
        userService.createUser(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * 헤더에 클리어 해야할 토큰 정보를 추가합니다.
     */
    @PostMapping("/users")
    public ResponseEntity createUser(HttpServletRequest request, @RequestBody RequestCreateUserDto dto)
        throws EmailNotVerifiedException, EmailDuplicateException {
        userService.createUser(request, dto);
        HttpHeaders headers = CreateHeaders.createClearTokenHeaders(JwtTokenProvider.EMAIL);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<ResponseListDto> getUsers(@RequestBody RequestUsersDto requestDto) {
        ResponseListDto<ResponseGetUserDto> users = userService.getUsers(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity updateUser(HttpServletRequest request, @RequestBody RequestUpdateUserDto dto)
        throws EmailNotVerifiedException {
        userService.updateUser(request, dto);
        HttpHeaders headers = CreateHeaders.createClearTokenHeaders(JwtTokenProvider.EMAIL);
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @PutMapping("/admin/users/{userId}/role")
    public ResponseEntity updateUserRoleGuestToUser(@PathVariable Long userId) {
        userService.updateUserRoleGuestToUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity updateUserStatusDeleteByAdmin(@PathVariable Long userId) {
        userService.updateUserStatusDelete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity updateUserStatusDelete(@AuthenticationPrincipal String username, @PathVariable Long userId) {
        userService.updateUserStatusDelete(username, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
