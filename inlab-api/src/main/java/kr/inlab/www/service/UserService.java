package kr.inlab.www.service;

import javax.servlet.http.HttpServletRequest;
import kr.inlab.www.common.exception.EmailNotVerifiedException;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

// todo [Login]1-5. spring security 가 주관하는 인증 서비스를 사용할 수 있도록 UserDetailsService 상속한다.
public interface UserService extends UserDetailsService {

    User findUserById(Long userId);

    void createUser(RequestCreateUserDto dto);

    void createUser(HttpServletRequest request, RequestCreateUserDto dto) throws EmailNotVerifiedException;

    void resetLoginAttempt(String username);

    User findUserByEmail(String username);

    void updateUserStatusBlock(String email);

    void increaseLoginAttempt(String email);
}
