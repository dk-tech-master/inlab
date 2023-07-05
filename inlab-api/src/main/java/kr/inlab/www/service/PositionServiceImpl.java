package kr.inlab.www.service;

import kr.inlab.www.common.exception.PositionDeleteNotAllowedException;
import kr.inlab.www.common.exception.PositionAlreadyExistsException;
import kr.inlab.www.common.exception.PositionNotFoundException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestPositionDto;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.dto.response.ResponsePositionDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.repository.PositionRepository;
import kr.inlab.www.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService{

    private final PositionRepository positionRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public void createPosition(RequestPositionNameDto requestDto) {
        if(positionRepository.existsByPositionName(requestDto.getPositionName()))
            throw new PositionAlreadyExistsException();

        Position position = Position.builder()
                .positionName(requestDto.getPositionName())
                .build();
        positionRepository.save(position);
    }

    @Override
    public ResponseListDto<ResponsePositionDto> getPosition(RequestPositionDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), Sort.Direction.DESC, "positionId");
        Page<ResponsePositionDto> positionList = positionRepository.getPositionsList(requestDto.getPositionName(), pageable);

        PagingUtil pagingUtil = new PagingUtil(positionList.getTotalElements(), positionList.getTotalPages(), positionList.getNumber(), positionList.getSize());

        return new ResponseListDto<ResponsePositionDto>(positionList.getContent(),pagingUtil);

    }

    @Override
    @Transactional
    public void deletePosition(Integer positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(PositionNotFoundException::new);

        positionRepository.delete(position);
    }

    @Override
    @Transactional
    public void updatePosition(Integer positionId , RequestPositionNameDto requestDto) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(PositionNotFoundException::new);

        if(questionRepository.countByPosition(position) > 0)
            throw new PositionDeleteNotAllowedException();

        position.updateName(requestDto.getPositionName());
    }
}
