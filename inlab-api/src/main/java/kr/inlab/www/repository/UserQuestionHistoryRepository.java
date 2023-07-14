package kr.inlab.www.repository;

import kr.inlab.www.dto.response.ResponseUserQuestionHistoryInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.inlab.www.entity.UserQuestionHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserQuestionHistoryRepository extends JpaRepository<UserQuestionHistory, Long> {

    @Query(value = "select uqh.userQuestionHistoryId as userQuestionHistoryId, " +
            " qv.title as questionTitle, " +
            "qv.version as questionVersion, " +
            "u.nickname as nickname, " +
            "uqh.createdAt as readingTime " +
            "from UserQuestionHistory uqh " +
            "join User u on u.userId = uqh.user.userId " +
            "join Question q on q.questionId = uqh.question.questionId " +
            "join QuestionVersion  qv on qv.question.questionId = q.questionId " +
            "where qv.isLatest = 'Y' and ((:keyword is null or u.nickname like concat('%', :keyword, '%')) or (:keyword is null or qv.title like concat('%', :keyword, '%'))) ")
    Page<ResponseUserQuestionHistoryInterface> findUserQuestionHistoryList(@Param("keyword") String keyword, Pageable pageable);
}
