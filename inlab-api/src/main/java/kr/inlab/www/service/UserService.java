package kr.inlab.www.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

import kr.inlab.www.common.exception.EmailDuplicateException;
import kr.inlab.www.common.exception.EmailNotVerifiedException;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.dto.request.RequestUpdateUserDto;
import kr.inlab.www.dto.request.RequestUsersDto;
import kr.inlab.www.dto.response.ResponseGetUserDto;
import kr.inlab.www.entity.User;

// todo [Login]1-5. spring security 가 주관하는 인증 서비스를 사용할 수 있도록 UserDetailsService 상속한다.
public interface UserService extends UserDetailsService {

    ResponseGetUserDto getUserById(Long userId);

    void createUser(RequestCreateUserDto dto) throws EmailDuplicateException;

    void createUser(HttpServletRequest request, RequestCreateUserDto dto)
        throws EmailNotVerifiedException, EmailDuplicateException;

    void resetLoginAttempt(String username);

    User getUserByEmail(String username);

    void increaseLoginAttempt(String email);

    void updateUser(HttpServletRequest request, RequestUpdateUserDto dto);

    boolean isEmailDuplicate(String email);

    boolean isNicknameDuplicate(String nickname);

    boolean isNicknameDuplicateForUpdate(String nickname, String email);

    void updateUserRoleGuestToUser(Long userId);

    ResponseListDto getUsers(RequestUsersDto requestDto);

    void updateUserStatusDeleteByAdmin(Long userId);

    void updateUserStatusDelete(Long userId);

    void updateUserBlockUntil(String email);

    boolean isSelf(Long userId);

    boolean isAdminOrSelf(Long userId);

    boolean isAdmin();

    String getLoginUserNickname();
}
