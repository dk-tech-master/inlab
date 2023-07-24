package kr.inlab.www.controller;

import java.util.List;
import javax.management.relation.RoleNotFoundException;
import kr.inlab.www.dto.common.PositionAndLevelList;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestGetPositionDto;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.dto.response.ResponsePositionDto;
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
    public ResponseEntity createPosition(@RequestBody RequestPositionNameDto requestDto) throws RoleNotFoundException {
        positionService.createPosition(requestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity getPosition(@ModelAttribute RequestGetPositionDto requestDto) {
        ResponseListDto<ResponsePositionDto> responseDto = positionService.getPosition(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/category")
    public ResponseEntity getPosition() {
        List<PositionAndLevelList> positionOnCategory = positionService.getPositionOnCategory();
        return ResponseEntity.ok(positionOnCategory);
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
