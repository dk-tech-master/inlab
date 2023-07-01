package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestPositionDto;
import kr.inlab.www.repository.PositionRepository;
import kr.inlab.www.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/position")
public class PositionController {

    private final PositionService positionService;
    @PostMapping
    public ResponseEntity<?> createPosition(@RequestBody RequestPositionDto dto){
        positionService.createPosition(dto.getPositionName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
