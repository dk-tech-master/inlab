package kr.inlab.www.common.exception.handler;

import kr.inlab.www.common.exception.InlabException;
import kr.inlab.www.dto.common.ResponseErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InlabException.class)
    public ResponseEntity<ResponseErrorDto> inlabException(InlabException e) {
        int statusCode = e.getStatusCode();

        ResponseErrorDto body = ResponseErrorDto.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode).body(body);
    }
}
