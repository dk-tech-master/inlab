package kr.inlab.www.service;

import kr.inlab.www.common.exception.QuestionTypeNotFoundException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestQuestionTypeDto;
import kr.inlab.www.dto.request.RequestQuestionTypeNameDto;
import kr.inlab.www.dto.response.ResponseQuestionTypeDto;
import kr.inlab.www.entity.QuestionType;
import kr.inlab.www.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionTypeServiceImpl implements QuestionTypeService {

    private final QuestionTypeRepository questionTypeRepository;

    @Override
    @Transactional
    public void createQuestionType(RequestQuestionTypeNameDto requestDto) {
        QuestionType questionType = QuestionType.builder()
                .questionTypeName(requestDto.getQuestionTypeName())
                .build();
       questionTypeRepository.save(questionType);
    }

    @Override
    public ResponseListDto<ResponseQuestionTypeDto> getQuestionType(RequestQuestionTypeDto requestDto) {
        requestDto.setColumn("questionTypeId");
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSortDirection(), requestDto.getColumn());
        Page<ResponseQuestionTypeDto> questionTypeList = questionTypeRepository.getQuestionTypeList(requestDto.getQuestionTypeName(), pageable);

        PagingUtil pagingUtil = new PagingUtil(questionTypeList.getTotalElements(), questionTypeList.getTotalPages(), questionTypeList.getNumber(), questionTypeList.getSize());
        List<ResponseQuestionTypeDto> content = questionTypeList.getContent();

        return new ResponseListDto<>(content, pagingUtil);
    }

    @Override
    @Transactional
    public void updateQuestionType(Integer questionTypeId, RequestQuestionTypeNameDto reqeustDto) {
        QuestionType questionType = questionTypeRepository.findById(questionTypeId)
                .orElseThrow(QuestionTypeNotFoundException::new);

        questionType.updateQuestionTypeName(reqeustDto.getQuestionTypeName());
    }

    @Override
    @Transactional
    public void deleteQuestionType(Integer questionTypeId) {
        QuestionType questionType = questionTypeRepository.findById(questionTypeId)
                .orElseThrow(QuestionTypeNotFoundException::new);

        questionTypeRepository.delete(questionType);
    }
}
