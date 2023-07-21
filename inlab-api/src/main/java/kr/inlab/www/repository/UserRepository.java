package kr.inlab.www.repository;

import java.util.List;
import java.util.Optional;
import kr.inlab.www.entity.Role;
import kr.inlab.www.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByEmail(String username);

    boolean existsByNickname(String nickname);

    boolean existsByNicknameAndEmailNot(String nickname, String username);

    boolean existsByEmail(String email);

    List<User> findAllByRoles(Role role);
}
