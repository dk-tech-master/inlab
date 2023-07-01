package kr.inlab.www.service;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import kr.inlab.www.common.exception.AccountBlockedException;
import kr.inlab.www.common.exception.EmailMismatchException;
import kr.inlab.www.common.exception.EmailNotVerifiedException;
import kr.inlab.www.common.type.RoleType;
import kr.inlab.www.common.type.UserStatus;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.entity.Role;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.RoleRepository;
import kr.inlab.www.repository.UserRepository;
import kr.inlab.www.security.CustomUserDetails;
import kr.inlab.www.security.filter.AuthenticationFilter;
import kr.inlab.www.security.jwt.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final BCryptPasswordEncoder encoder;
    // todo [Authorization]1-2. 초기 기동을 할 때 빈을 주입을 받는데 encoder 는 빈으로 등록하지 않았다 따라서 bean 을 등록해야한다.

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public void createUser(RequestCreateUserDto dto) {
        User user = User.builder()
            .email(dto.getEmail())
            .nickname(dto.getNickname())
            .password(encoder.encode(dto.getPassword()))
            .build();
        userRepository.save(user);

        Role role = Role.builder()
            .user(user)
            .roleType(RoleType.ROLE_GUEST)
            .build();
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void createUser(HttpServletRequest request, RequestCreateUserDto dto) throws EmailNotVerifiedException {
        compareEmailFromTokenAndForm(request, dto);
        User user = User.builder()
            .email(dto.getEmail())
            .nickname(dto.getNickname())
            .password(encoder.encode(dto.getPassword()))
            .build();
        userRepository.save(user);

        Role role = Role.builder()
            .user(user)
            .roleType(RoleType.ROLE_GUEST)
            .build();
        roleRepository.save(role);
    }

    private void compareEmailFromTokenAndForm(HttpServletRequest request, RequestCreateUserDto dto)
        throws EmailNotVerifiedException {
        if (!Objects.equals(dto.getEmail(), getEmailFromToken(request))) {
            throw new EmailMismatchException();
        }
    }

    private String getEmailFromToken(HttpServletRequest request) throws EmailNotVerifiedException {
        String emailToken = jwtTokenProvider.getEmailTokenFromRequest(request);
        if (Objects.isNull(emailToken)) {
            throw new EmailNotVerifiedException();
        }
        return jwtTokenProvider.getEmailFromToken(emailToken);
    }

    /**
     * user 를 찾았는데 없으면  {@link AuthenticationFilter} 의 unsuccessfulAuthentication 로 이동한다. user 의 상태가 Block 이면 {@link
     * AuthenticationFilter} 의 unsuccessfulAuthentication 로 이동한다.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("loadUserByUsername exception!!!");
        });

        if (Objects.equals(user.getUserStatus(), UserStatus.BLOCK)) {
            throw new AccountBlockedException();
        }
        user.getRoles().size(); // roles 컬렉션을 초기화하기 위해 Hibernate 세션을 강제로 호출합니다.
        return new CustomUserDetails(user);
    }

    @Override
    @Transactional
    public void resetLoginAttempt(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("resetLoginAttempt exception!!!");
        });
        user.resetLoginAttempt();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("findUserByEmail exception!!!");
        });
    }

    @Override
    @Transactional
    public void updateUserStatusBlock(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("resetLoginAttempt exception!!!");
        });
        user.updateUserStatusBlock();
    }

    @Override
    @Transactional
    public void increaseLoginAttempt(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException("resetLoginAttempt exception!!!");
        });
        user.incrementLoginAttempt();
    }
}
