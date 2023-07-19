package kr.inlab.www.controller;

import kr.inlab.www.dto.response.ResponseGetAllPositionLevelDto;
import kr.inlab.www.service.CategoryService;
import kr.inlab.www.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity getAllPositionAndLevel() {
        ResponseGetAllPositionLevelDto allPositionAndLevelList = categoryService.getAllPositionAndLevelList();
        return ResponseEntity.status(HttpStatus.OK).body(allPositionAndLevelList);
    }
}
