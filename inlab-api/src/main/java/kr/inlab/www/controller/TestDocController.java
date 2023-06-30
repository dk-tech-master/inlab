package kr.inlab.www.controller;

import kr.inlab.www.dto.common.RequestTestRestDocsDto;
import kr.inlab.www.dto.common.ResponseTestRestDocsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/test-rest-docs")
public class TestDocController {

    @GetMapping("/{testPath}")
    public ResponseEntity<?> getTestRestDocs(@RequestParam String testParam, @PathVariable String testPath) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseTestRestDocsDto("result: " + testPath + ", " + testParam));
    }

    @PostMapping
    public ResponseEntity<?> postTestRestDocs(@RequestBody RequestTestRestDocsDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseTestRestDocsDto(requestDto.toString()));
    }
}
