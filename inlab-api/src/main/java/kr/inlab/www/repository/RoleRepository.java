package kr.inlab.www.repository;

import java.util.List;
import java.util.Optional;
import kr.inlab.www.common.type.RoleType;
import kr.inlab.www.entity.Role;
import kr.inlab.www.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByUser(User user);

    Role findByUserAndRoleType(User user, RoleType roleGuest);

    Optional<Role> findByRoleType(RoleType roleType);
}
