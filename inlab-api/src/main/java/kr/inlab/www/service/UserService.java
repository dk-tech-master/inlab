package kr.inlab.www.service;

import kr.inlab.www.dto.request.UserCreateRequestDto;
import kr.inlab.www.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

// todo [Login]1-5. spring security 가 주관하는 인증 서비스를 사용할 수 있도록 UserDetailsService 상속한다.
public interface UserService extends UserDetailsService {

    User findUserById(Long userId);

    boolean createUser(UserCreateRequestDto dto);
}
