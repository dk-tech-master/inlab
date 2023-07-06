package kr.inlab.www.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.List;
import kr.inlab.www.dto.request.RequestCreateUserDto;
import kr.inlab.www.dto.request.RequestUpdateUserDto;
import kr.inlab.www.dto.request.RequestUsersDto;
import kr.inlab.www.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Principal principal;

    @BeforeEach
    private void setUpUser() {
        String username = "jwoo1016@naver.com";
        String nickname = "최정우";
        String password = "1234";
        User user = User.builder()
            .email(username)
            .nickname(nickname)
            .password(password)
            .build();

        principal = new UsernamePasswordAuthenticationToken(
            username, password, List.of());
    }

    @Test
    @Transactional
    @WithMockUser(username = "jwoo1016@naver.com", authorities = {"ROLE_ADMIN"})
    void getUser() throws Exception {

        Long userId = 1L;

        User user = User.builder()
            .userId(userId)
            .email("jwoo1016@naver.com")
            .nickname("최정우")
            .build();

        // Act
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/users/{userId}", userId)
                    .header(HttpHeaders.AUTHORIZATION, "jwt token"))
            .andExpect(status().isOk());

        // Assert
        resultActions.andExpect(jsonPath("$.userId").value(userId))
            .andDo(document("get-user",
                pathParameters(
                    parameterWithName("userId").description("사용자 ID")
                ),
                requestHeaders(headerWithName("Authorization").description("JWT Token")),
                responseFields(
                    fieldWithPath("userId").description("사용자 ID"),
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("nickname").description("닉네임"),
                    fieldWithPath("isVerified").description("승인 여부"),
                    fieldWithPath("createdAt").description("가입 일자")
                    // 필요한 만큼 사용자 정보를 추가하세요
                )
            ));
    }

    @Test
    @Transactional
    void createUser() throws Exception {
        RequestCreateUserDto dto = new RequestCreateUserDto();
        dto.setEmail("jwoo1016@gmail.com");
        dto.setPassword("1234");
        dto.setNickname("최정우지메일");

        // Act
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.post("/api/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto))
                    .header("email",
                        "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp3b28xMDE2QGdtYWlsLmNvbSIsImV4cCI6MTY5NzI0OTAwMSwiaWF0IjoxNjg4NjA5MDAxfQ.ESFBus61BWK5tn1YbEpzfCUGrmSRuH0wgKCDvCVmAHs"))
            .andExpect(status().isCreated());

        resultActions.andDo(document("create-user",
            requestFields(
                fieldWithPath("email").description("이메일"),
                fieldWithPath("nickname").description("닉네임"),
                fieldWithPath("password").description("비밀번호")
            ),
            responseHeaders(
                headerWithName("Clear-Token").description("저장되었던 토큰을 지우라는 토큰 값에 지워야하는 토큰의 정보의 key 가 들어있다.")
            )
        ));
    }

    @Test
    @Transactional
    void getUsers() throws Exception {
        RequestUsersDto dto = new RequestUsersDto();
        new PageRequestDto();

        // 필드 값을 세팅할 값
        String nickname = "최정우";
        Boolean isVerified = true;
        Integer page = 1;

        // Reflection을 이용하여 필드 값을 세팅
        try {
            Field nicknameField = dto.getClass().getDeclaredField("nickname");
            Field isVerifiedField = dto.getClass().getDeclaredField("isVerified");
            Field pageField = dto.getClass().getSuperclass().getDeclaredField("page");
            nicknameField.setAccessible(true);
            isVerifiedField.setAccessible(true);
            pageField.setAccessible(true);
            nicknameField.set(dto, nickname);
            isVerifiedField.set(dto, isVerified);
            pageField.set(dto, page);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // Act
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/admin/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto))
                    .header(HttpHeaders.AUTHORIZATION,
                        "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imp3b28xMDE2QG5hdmVyLmNvbSIsInJvbGVzIjpbIlJPTEVfR1VFU1QiXSwiZXhwIjoxNjk3MDEwMzE0LCJpYXQiOjE2ODgzNzAzMTR9.OvbpnBc3rL51hI26yLYiJMp-sTma_wnVHmL2vyn4Fiw"))
            .andExpect(status().isOk());

        resultActions.andDo(document("get-users",
            requestFields(
                fieldWithPath("nickname").description("검색에 사용되는 닉네임"),
                fieldWithPath("isVerified").description("인증여부"),
                fieldWithPath("page").description("페이지"),
                fieldWithPath("pageSize").description("페이지 사이즈"),
                fieldWithPath("sortDirection").description("정렬 순서"),
                fieldWithPath("column").description("정렬 필드")
            )
        ));
    }

    @Test
    @Transactional
    void updateUser() throws Exception {
        RequestUpdateUserDto dto = new RequestUpdateUserDto();
        dto.setEmail("jwoo1016@naver.com");
        dto.setNickname("최정우변경");
        dto.setPassword("1234");

        Long userId = 1L;
        // Act
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.put("/api/users/{userId}",userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto))
                    .header("email",
                        "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp3b28xMDE2QG5hdmVyLmNvbSIsImV4cCI6MTY5NzI1MTc2OCwiaWF0IjoxNjg4NjExNzY4fQ.Xz26dLKW23QCfw7E1yXd64GvmOoIK02MujjaIS2l0aA"))
            .andExpect(status().isOk());

        resultActions.andDo(document("update-user",
            requestFields(
                fieldWithPath("email").description("이메일"),
                fieldWithPath("nickname").description("닉네임"),
                fieldWithPath("password").description("비밀번호")
            ),
            responseHeaders(
                headerWithName("Clear-Token").description("저장되었던 토큰을 지우라는 토큰 값에 지워야하는 토큰의 정보의 key 가 들어있다.")
            )
        ));
    }

    @Test
    @Transactional
    void updateUserRoleGuestToUser() throws Exception {
        Long userId = 2L;
        // Act
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.put("/api/admin/users/{userId}/role", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        resultActions.andDo(document("update-user-role")
        );
    }

    @Test
    @Transactional
    void updateUserStatusDeleteByAdmin() throws Exception {
        Long userId = 2L;
        // Act
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/api/admin/users/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        resultActions.andDo(document("delete-user-by-admin")
        );
    }

    @Test
    @Transactional
    @WithMockUser(username = "jwoo1017@naver.com", authorities = {"ROLE_USER"})
    void updateUserStatusDelete() throws Exception {
        Long userId = 2L;
        // Act
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/api/users/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        resultActions.andDo(document("delete-user")
        );
    }
}