package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestPositionDto;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.dto.response.ResponsePositionListDto;
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
    public ResponseEntity createPosition(@RequestBody RequestPositionNameDto requestDto) {
        positionService.createPosition(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<ResponsePositionListDto> getPosition(RequestPositionDto requestDto) {
        ResponsePositionListDto responseDto = positionService.getPosition(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{positionId}")
    public ResponseEntity deletePosition(@PathVariable Integer positionId){
        positionService.deletePosition(positionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{positionId}")
    public ResponseEntity updatePosition(@RequestBody RequestPositionNameDto requestDto, @PathVariable Integer positionId) {
        positionService.updatePosition(positionId,requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
