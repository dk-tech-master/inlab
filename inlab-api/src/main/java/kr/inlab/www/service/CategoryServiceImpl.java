package kr.inlab.www.service;

import kr.inlab.www.dto.response.ResponseGetAllPositionLevelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final PositionService positionService;
    private final QuestionLevelService questionLevelService;

    @Override
    public ResponseGetAllPositionLevelDto getAllPositionAndLevelList() {
        return ResponseGetAllPositionLevelDto.builder()
                .positionList(positionService.getAllPosition())
                .questionLevelList(questionLevelService.getAllQuestionLevel())
                .build();
    }

}
