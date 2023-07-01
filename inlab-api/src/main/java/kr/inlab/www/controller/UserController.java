package kr.inlab.www.controller;

import javax.servlet.http.HttpServletRequest;
import kr.inlab.www.common.exception.EmailNotVerifiedException;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.entity.User;
import kr.inlab.www.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/health_check")
    public String healthCheck() {
        return "good_health";
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    /**
     * 원래 회원가입은 이메일 인증을 하고 jwt 토큰 까서 내가 가입시키려는 이메일이 유효한 이메일인지 확인하는데
     *
     * 개발할 때 간단하게 회원 집어넣는 메서드 입니다.
     */
    @PostMapping("/easy")
    public ResponseEntity createEasyUser(@RequestBody RequestCreateUserDto dto) {
        userService.createUser(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @PostMapping
    public ResponseEntity createUser(HttpServletRequest request, @RequestBody RequestCreateUserDto dto)
        throws EmailNotVerifiedException {
        userService.createUser(request, dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal String username) {
        System.out.println(username);
        return "123";
    }
}
