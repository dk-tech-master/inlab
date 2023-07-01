package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestPositionDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.repository.PositionRepository;
import kr.inlab.www.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/position")
public class PositionController {
    private final int PAGE_SIZE = 5;
    private final PositionService positionService;
    private final PositionRepository positionRepository;

    @PostMapping
    public ResponseEntity<?> createPosition(@RequestBody RequestPositionDto dto){
        positionService.createPosition(dto.getPositionName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
