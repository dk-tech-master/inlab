package kr.inlab.www.dto.response;

import java.time.LocalDateTime;

public interface ResponseUserQuestionHistoryInterface {

    Long getUserQuestionHistoryId();

    String getQuestionTitle();

    String getQuestionVersion();

    String getNickname();

    LocalDateTime getReadingTime();
}
