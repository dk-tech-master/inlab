package kr.inlab.www.repository;

import kr.inlab.www.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepositoryCustom {

    Page<User> findUsers(String nickname,
        Boolean isVerified,
        Pageable pageable);
}
