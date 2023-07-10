package kr.inlab.www.controller;

import kr.inlab.www.dto.request.RequestUpdatePositionLevelDto;
import kr.inlab.www.service.PositionLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/position_level")
public class PositionLevelController {

    private final PositionLevelService positionLevelService;

    @PreAuthorize("@userServiceImpl.isAdminOrSelf(#userId)")
    @GetMapping
    public ResponseEntity getPositionLevel(@RequestParam Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(positionLevelService.getPositionLevel(userId));
    }

    @PutMapping
    public ResponseEntity updatePositionLevel(@RequestBody RequestUpdatePositionLevelDto requestDto) {
        positionLevelService.updatePositionLevel(requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
