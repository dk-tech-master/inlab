package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestUserQuestionHistoryListDto;

public interface UserQuestionHistoryService {

    ResponseListDto getUserQuestionHistoryList(RequestUserQuestionHistoryListDto requestDto);

    void createUserQuestionHistory(Long questionId);
}
