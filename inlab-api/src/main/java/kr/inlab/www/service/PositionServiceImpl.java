package kr.inlab.www.service;

import kr.inlab.www.common.exception.PositionNotFoundException;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.request.RequestPositionDto;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.dto.response.ResponsePositionDto;
import kr.inlab.www.dto.response.ResponsePositionListDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService{

    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public void createPosition(RequestPositionNameDto requestDto) {
        Position position = Position.builder()
                .positionName(requestDto.getPositionName())
                .build();
        positionRepository.save(position);
    }

    @Override
    public ResponsePositionListDto getPosition(RequestPositionDto requestDto) {
        requestDto.setColumn("positionId");
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSortDirection(), requestDto.getColumn());
        Page<ResponsePositionDto> positionList = positionRepository.getPositionsList(requestDto.getPositionName(), pageable);
        ResponsePositionListDto responsePositionListDto = ResponsePositionListDto.builder()
                .pagingUtil(new PagingUtil(positionList.getTotalElements(), positionList.getTotalPages(), positionList.getNumber(), positionList.getSize()))
                .positionList(positionList.toList())
                .build();
        return responsePositionListDto;
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

        position.updateName(requestDto.getPositionName());
    }
}
