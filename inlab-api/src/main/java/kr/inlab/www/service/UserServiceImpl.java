package kr.inlab.www.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import kr.inlab.www.common.exception.AccountDeletedException;
import kr.inlab.www.common.exception.AdminCouldNotDeleteException;
import kr.inlab.www.common.exception.EmailDuplicateException;
import kr.inlab.www.common.exception.EmailMismatchException;
import kr.inlab.www.common.exception.EmailNotFoundException;
import kr.inlab.www.common.exception.EmailNotVerifiedException;
import kr.inlab.www.common.exception.LoginBlockedException;
import kr.inlab.www.common.exception.NicknameDuplicateException;
import kr.inlab.www.common.exception.UnAuthorizedException;
import kr.inlab.www.common.exception.UserNotFoundException;
import kr.inlab.www.common.type.RoleType;
import kr.inlab.www.common.type.UserStatus;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.dto.request.RequestUpdateUserDto;
import kr.inlab.www.dto.request.RequestUsersDto;
import kr.inlab.www.dto.response.ResponseUserDto;
import kr.inlab.www.entity.Role;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.RoleRepository;
import kr.inlab.www.repository.UserRepository;
import kr.inlab.www.security.CustomUserDetails;
import kr.inlab.www.security.filter.AuthenticationFilter;
import kr.inlab.www.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createUser(RequestCreateUserDto dto) throws EmailDuplicateException {
        if (isEmailDuplicate(dto.getEmail())) {
            throw new EmailDuplicateException();
        }
        if (isNicknameDuplicate(dto.getNickname())) {
            throw new NicknameDuplicateException();
        }
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
    public void createUser(HttpServletRequest request, RequestCreateUserDto dto)
        throws EmailNotVerifiedException, EmailDuplicateException {
        if (isEmailDuplicate(dto.getEmail())) {
            throw new EmailDuplicateException();
        }
        if (isNicknameDuplicate(dto.getNickname())) {
            throw new NicknameDuplicateException();
        }
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

    @Override
    @Transactional
    public void updateUser(HttpServletRequest request, RequestUpdateUserDto dto) throws EmailNotVerifiedException {
        compareEmailFromTokenAndForm(request, dto);
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(EmailNotFoundException::new);
        if (isNicknameDuplicateForUpdate(dto.getNickname(), dto.getEmail())) {
            throw new NicknameDuplicateException();
        }
        user.updatePassword(encoder.encode(dto.getPassword()));
        user.updateNickname(dto.getNickname());
    }

    private void compareEmailFromTokenAndForm(HttpServletRequest request, RequestCreateUserDto dto)
        throws EmailNotVerifiedException {
        if (!Objects.equals(dto.getEmail(), getEmailFromToken(request))) {
            throw new EmailMismatchException();
        }
    }

    private void compareEmailFromTokenAndForm(HttpServletRequest request, RequestUpdateUserDto dto)
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
            throw new UserNotFoundException();
        });

        if (Objects.equals(user.getUserStatus(), UserStatus.DELETE)) {
            throw new AccountDeletedException();
        }

        if (user.getLoginBlockUntil().isAfter(LocalDateTime.now())) {
            throw new LoginBlockedException();
        }
        user.getRoles().size(); // roles 컬렉션을 초기화하기 위해 Hibernate 세션을 강제로 호출합니다.
        return new CustomUserDetails(user);
    }

    @Override
    @Transactional
    public void resetLoginAttempt(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        user.resetLoginAttempt();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
    }

    @Override
    @Transactional
    public void increaseLoginAttempt(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        user.incrementLoginAttempt();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isNicknameDuplicateForUpdate(String nickname, String email) {
        return userRepository.existsByNicknameAndEmailNot(nickname, email);
    }

    @Override
    @Transactional
    public void updateUserRoleGuestToUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Role role = Role.builder()
            .user(user)
            .roleType(RoleType.ROLE_USER)
            .build();
        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseListDto<ResponseUserDto> getUsers(RequestUsersDto requestDto) {
        if (requestDto.getColumn() == null || "".equals(requestDto.getColumn())) {
            requestDto.setColumn("user_id");
        }

        PageRequest pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(),
            requestDto.getSortDirection(), requestDto.getColumn());
        // todo findAll 채우기
        Page<User> userList = userRepository.findUsers(requestDto.getNickname(),
            requestDto.getIsVerified(), pageable);

        List<ResponseUserDto> collect = userList.getContent().stream().map(User::toResponseUserDto)
            .collect(Collectors.toList());

        PagingUtil pagingUtil = new PagingUtil(userList.getTotalElements(), userList.getTotalPages(),
            userList.getNumber(),
            userList.getSize());
        return new ResponseListDto<>(collect, pagingUtil);
    }

    @Override
    @Transactional
    public void updateUserStatusDelete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getRoles().stream().map(Role::getRoleType)
            .anyMatch(roleType -> roleType.equals(RoleType.ROLE_ADMIN))) {
            throw new AdminCouldNotDeleteException();
        }
        user.updateUserStatusDelete();
    }

    @Override
    @Transactional
    public void updateUserStatusDelete(String username, Long userId) {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
        if (!user.getUserId().equals(userId)) {
            throw new UnAuthorizedException();
        }
        user.updateUserStatusDelete();
    }

    @Override
    @Transactional
    public void updateUserBlockUntil(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.updateLoginBlockUntil();
        user.resetLoginAttempt();
    }
}
