package kr.inlab.www.common.exception.handler;

import java.util.Objects;
import kr.inlab.www.common.exception.InlabException;
import kr.inlab.www.dto.common.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
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

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseErrorDto> dtoBindingException(BindException e) {
        ResponseErrorDto body = ResponseErrorDto.builder()
            .code(HttpStatus.BAD_REQUEST.toString())
            .message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage())
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
