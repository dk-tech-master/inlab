package kr.inlab.www.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.inlab.www.common.util.CreateHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden 응답 설정

        // todo sendError 로 처리를 할까 아니면 그냥 헤더에 값을 넣어줘서 구현을 하도록 할까
        response.addHeader(CreateHeaders.X_UNAUTHORIZED_USER,CreateHeaders.TRUE);
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }
}
