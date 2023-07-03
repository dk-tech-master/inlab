package kr.inlab.www.service;

import kr.inlab.www.entity.Position;
import kr.inlab.www.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService{

    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public boolean createPosition(String name) {
        Position position = Position.builder()
                .positionName(name)
                .build();
        positionRepository.save(position);

        return true;
    }
}
