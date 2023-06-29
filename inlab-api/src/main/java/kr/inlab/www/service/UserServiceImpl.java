package kr.inlab.www.service;

import java.util.Optional;
import javax.transaction.Transactional;
import kr.inlab.www.common.util.DtoConverter;
import kr.inlab.www.dto.request.UserCreateRequestDto;
import kr.inlab.www.entity.Role;
import kr.inlab.www.common.type.RoleType;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.RoleRepository;
import kr.inlab.www.repository.UserRepository;
import kr.inlab.www.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    // todo [Authorization]1-2. 초기 기동을 할 때 빈을 주입을 받는데 encoder 는 빈으로 등록하지 않았다 따라서 bean 을 등록해야한다.

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    @Transactional
    public boolean createUser(UserCreateRequestDto dto) {
        User user = DtoConverter.convert(dto);
        // todo [Authorization]1-4. 암호화 해서 user를 저장한다.
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        roleRepository.save(new Role(user, RoleType.ROLE_GUEST));
        return true;
    }

    // todo [Login]1-6. spring security 가 주관하는 인증 서비스이다. 여기서는 username(우리 서비스의 경우 이메일)을 가지고 db 에서 정보를 찾는다.
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // todo [Login]1-7. email 을 가지고 db 뒤졌는데 없다? 그러면 예외를 던진다.
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("너의 이메일은 없다");
        }
        // todo [Login]1-8. email 을 가지고 db 뒤졌는데 있다? db 에서 찾은 pwd 와 로그인 시 입력한 pwd 를 비교하기위한 클래스를 만들고 반환한다.
        // think  question : 왜 귀찮게 클래스를 만들고 반환하죠? db 에서 찾은 암호화된 pwd 만 비교하면 되니까 pwd 만 반환해도되는데
        // think  answer : 비밀번호만 반환하면 인증에 필요한 사용자의 아이디, 권한, 계정 잠금 상태 등의 중요한 정보를 알 수 없기 때문에 UserDetails 인터페이스를 구현한 객체를 생성하여 반환하는 것이 일반적입니다.
        User user = optionalUser.get();
        user.getRoles().size(); // roles 컬렉션을 초기화하기 위해 Hibernate 세션을 강제로 호출합니다.
        return new CustomUserDetails(user);
    }
}
