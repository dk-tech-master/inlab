package kr.inlab.www.repository;

import kr.inlab.www.dto.response.ResponseInterviewDto;
import kr.inlab.www.entity.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    boolean existsByTitle(String title);

    boolean existsByTitleAndInterviewIdNot(String title, Long interviewId);

    @Query("SELECT new kr.inlab.www.dto.response.ResponseInterviewDto(i.interviewId, i.title, u.nickname, COUNT(iq)) " +
            "FROM Interview i JOIN i.user u LEFT JOIN i.questions iq " +
            "WHERE u.userId = :userId AND i.title LIKE concat('%', :title, '%') " +
            "GROUP BY i.interviewId")
    Page<ResponseInterviewDto> getInterviewList(@Param("userId") Long userId, @Param("title") String title, Pageable pageable);
}

