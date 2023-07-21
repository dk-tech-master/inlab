package kr.inlab.www.service;

import kr.inlab.www.common.exception.QuestionNotFoundException;
import kr.inlab.www.common.exception.UserNotFoundException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestUserQuestionHistoryListDto;
import kr.inlab.www.dto.response.ResponseUserQuestionHistoryDto;
import kr.inlab.www.dto.response.ResponseUserQuestionHistoryInterface;
import kr.inlab.www.entity.Question;
import kr.inlab.www.entity.User;
import kr.inlab.www.entity.UserQuestionHistory;
import kr.inlab.www.repository.QuestionRepository;
import kr.inlab.www.repository.UserQuestionHistoryRepository;
import kr.inlab.www.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserQuestionHistoryServiceImpl implements UserQuestionHistoryService {

    private final UserQuestionHistoryRepository userQuestionHistoryRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseListDto getUserQuestionHistoryList(RequestUserQuestionHistoryListDto requestDto) {
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSortDirection(), "userQuestionHistoryId");
        Page<ResponseUserQuestionHistoryInterface> userQuestionHistoryList =
                userQuestionHistoryRepository.findUserQuestionHistoryList(requestDto.getKeyword(), pageRequest);
        PagingUtil pagingUtil =
                new PagingUtil(userQuestionHistoryList.getTotalElements(), userQuestionHistoryList.getTotalPages(), userQuestionHistoryList.getNumber(), userQuestionHistoryList.getSize());

        List<Object> responseList = userQuestionHistoryList.stream().map(responseUserQuestionHistoryInterface -> ResponseUserQuestionHistoryDto.builder()
                .questionHistoryId(responseUserQuestionHistoryInterface.getUserQuestionHistoryId())
                .questionTitle(responseUserQuestionHistoryInterface.getQuestionTitle())
                .questionVersion(responseUserQuestionHistoryInterface.getQuestionVersion())
                .nickname(responseUserQuestionHistoryInterface.getNickname())
                .readingTime(responseUserQuestionHistoryInterface.getReadingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build()
        ).collect(Collectors.toList());

        return ResponseListDto.builder()
                .responseList(responseList)
                .pagingUtil(pagingUtil)
                .build();
    }

    @Override
    @Transactional
    public void createUserQuestionHistory(Long questionId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        Question question = questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFoundException::new);

        UserQuestionHistory userQuestionHistory = UserQuestionHistory.builder()
                .user(user)
                .question(question)
                .build();

        userQuestionHistoryRepository.save(userQuestionHistory);
    }
}
