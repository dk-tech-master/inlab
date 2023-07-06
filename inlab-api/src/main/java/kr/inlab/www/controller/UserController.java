package kr.inlab.www.controller;

import javax.servlet.http.HttpServletRequest;
import kr.inlab.www.common.exception.EmailDuplicateException;
import kr.inlab.www.common.exception.EmailNotVerifiedException;
import kr.inlab.www.common.util.CreateHeaders;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.dto.request.RequestUpdateUserDto;
import kr.inlab.www.dto.request.RequestUsersDto;
import kr.inlab.www.security.jwt.JwtTokenProvider;
import kr.inlab.www.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * 회원이 자신의 정보를 조회하는 메서드
     * @param userId
     * @return
     * @PreAuthorize 가 false 를 반환하면 AccessDeniedException 가 발생한다.
     */
    @PreAuthorize("@userServiceImpl.isSelf(#userId)")
    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
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
     * @param request 인증 버튼을 누르면 받는 이메일 정보가 들어있는 jwt 토큰이 있습니다.
     * @param dto     회원 가입 양식에 적힌 정보입니다. 이메일, 닉네임, 비밀번호가 있습니다.
     * @throws EmailNotVerifiedException
     * @throws EmailDuplicateException   특이 사항: 헤더에 클리어 해야할 토큰 정보(email 토큰)를 추가합니다.
     */
    @PostMapping("/users")
    public ResponseEntity createUser(HttpServletRequest request, @RequestBody RequestCreateUserDto dto)
        throws EmailNotVerifiedException, EmailDuplicateException {
        userService.createUser(request, dto);
        HttpHeaders headers = CreateHeaders.createClearTokenHeaders(JwtTokenProvider.EMAIL);
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /**
     * 관리자가 면접관들을 다건 조회하는 메서드
     *
     * @param requestDto 검색조건(닉네임, 인증여부) 가 들어있습니다.
     * @return 검색 결과
     */
    @GetMapping("/admin/users")
    public ResponseEntity<ResponseListDto> getUsers(@RequestBody RequestUsersDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers(requestDto));
    }

    /**
     * 사용자가 자신의 정보를 수정하는 메서드
     *
     * @param request 이메일 인증을 하면 발급되는 email 토큰이 들어있습니다.
     * @param dto     수정할 회원정보가 들어있습니다.
     * @return
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity updateUser(HttpServletRequest request, @RequestBody RequestUpdateUserDto dto)
        throws EmailNotVerifiedException {
        userService.updateUser(request, dto);
        HttpHeaders headers = CreateHeaders.createClearTokenHeaders(JwtTokenProvider.EMAIL);
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    /**
     * 관리자가 회원의 권한을 ROLE_GUEST -> ROLE_USER 로 바꾸는 메서드입니다.
     * @param userId
     * @return
     */
    @PutMapping("/admin/users/{userId}/role")
    public ResponseEntity updateUserRoleGuestToUser(@PathVariable Long userId) {
        userService.updateUserRoleGuestToUser(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 관리자가 회원의 상태를 delete 로 바꾸는 메서드입니다.
     *
     * @param userId delete 될 회원의 userId
     * @return
     */
    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity updateUserStatusDeleteByAdmin(@PathVariable Long userId) {
        userService.updateUserStatusDeleteByAdmin(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 회원이 자신의 상태를 delete 로 바꾸는 메서드입니다.
     *
     * @param userId   회원의 userId
     * @return
     */
    @PreAuthorize("@userServiceImpl.isSelf(#userId)")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity updateUserStatusDelete(@PathVariable Long userId) {
        userService.updateUserStatusDelete(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
