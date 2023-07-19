package kr.inlab.www.service;

import kr.inlab.www.dto.common.QuestionLevelDto;
import kr.inlab.www.entity.QuestionLevel;
import kr.inlab.www.repository.QuestionLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionLevelServiceImpl implements QuestionLevelService {

    private final QuestionLevelRepository questionLevelRepository;
    @Override
    public List<QuestionLevelDto> getAllQuestionLevel() {
       return questionLevelRepository.findAll().stream().map(QuestionLevel::toDto).collect(Collectors.toList());
    }
}
