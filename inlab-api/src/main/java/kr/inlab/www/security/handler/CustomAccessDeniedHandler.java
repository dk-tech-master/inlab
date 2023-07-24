package kr.inlab.www.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.inlab.www.dto.common.ResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ResponseErrorDto responseErrorDto = ResponseErrorDto.builder()
            .code("UNAUTHORIZED")
            .message("사이트를 이용하려면 관리자의 승인이 필요합니다.")
            .build();
        String jsonResponse = objectMapper.writeValueAsString(responseErrorDto);
        response.getWriter().write(jsonResponse);
    }
}
