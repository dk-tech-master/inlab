package kr.inlab.www.controller;

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

    @PostMapping
    public ResponseEntity createUser(@RequestBody RequestCreateUserDto dto) {
        userService.createUser(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal String username) {
        System.out.println(username);
        return "123";
    }
}
