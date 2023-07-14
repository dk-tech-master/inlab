package kr.inlab.www.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import kr.inlab.www.common.exception.PositionNotFoundException;
import kr.inlab.www.common.exception.QuestionTypeAlreadyExistsException;
import kr.inlab.www.common.exception.QuestionTypeDeleteNotAllowedException;
import kr.inlab.www.common.exception.QuestionTypeNotFoundException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestCreateQuestionTypeDto;
import kr.inlab.www.dto.request.RequestGetQuestionTypeByPositionDto;
import kr.inlab.www.dto.request.RequestQuestionTypeDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionTypeDto;
import kr.inlab.www.dto.response.ResponseGetQuestionTypeByPositionDto;
import kr.inlab.www.dto.response.ResponseQuestionTypeDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.entity.QuestionType;
import kr.inlab.www.repository.PositionRepository;
import kr.inlab.www.repository.QuestionRepository;
import kr.inlab.www.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionTypeServiceImpl implements QuestionTypeService {

    private final QuestionTypeRepository questionTypeRepository;
    private final QuestionRepository questionRepository;
    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public void createQuestionType(RequestCreateQuestionTypeDto requestDto) {
        Integer positionId = requestDto.getPositionId();
        String questionTypeName = requestDto.getQuestionTypeName();
        if (questionTypeRepository.existsByPositionAndQuestionTypeName(positionId, questionTypeName)) {
            throw new QuestionTypeAlreadyExistsException();
        }
        Position position = positionRepository.findById(positionId).orElseThrow(PositionNotFoundException::new);
        QuestionType questionType = QuestionType.builder()
            .position(position)
            .questionTypeName(requestDto.getQuestionTypeName())
            .build();
        questionTypeRepository.save(questionType);
    }

    @Override
    public ResponseListDto<ResponseQuestionTypeDto> getQuestionType(RequestQuestionTypeDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(),
            Sort.by(Sort.Direction.DESC, "questionTypeId"));
        Page<ResponseQuestionTypeDto> questionTypeList = questionTypeRepository.getQuestionTypeList(
            requestDto.getQuestionTypeName(), pageable);

        PagingUtil pagingUtil = new PagingUtil(questionTypeList.getTotalElements(), questionTypeList.getTotalPages(),
            questionTypeList.getNumber(), questionTypeList.getSize());
        List<ResponseQuestionTypeDto> content = questionTypeList.getContent();

        return new ResponseListDto<>(content, pagingUtil);
    }

    @Override
    @Transactional
    public List<ResponseGetQuestionTypeByPositionDto> getQuestionTypeByPosition(
        RequestGetQuestionTypeByPositionDto requestDto) {
        return questionTypeRepository.findAllByPosition_PositionId(
            requestDto.getPositionId()).stream().map(questionType ->
                ResponseGetQuestionTypeByPositionDto
                    .builder()
                    .questionTypeId(questionType.getQuestionTypeId())
                    .questionTypeName(questionType.getQuestionTypeName())
                    .build())
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateQuestionType(Integer questionTypeId, RequestUpdateQuestionTypeDto requestDto) {
        QuestionType questionType = questionTypeRepository.findById(questionTypeId)
            .orElseThrow(QuestionTypeNotFoundException::new);

        questionType.updateQuestionTypeName(requestDto.getQuestionTypeName());
    }

    @Override
    @Transactional
    public void deleteQuestionType(Integer questionTypeId) {
        QuestionType questionType = questionTypeRepository.findById(questionTypeId)
            .orElseThrow(QuestionTypeNotFoundException::new);

        if (questionRepository.countByQuestionType(questionType) > 0) {
            throw new QuestionTypeDeleteNotAllowedException();
        }

        questionTypeRepository.delete(questionType);
    }
}
