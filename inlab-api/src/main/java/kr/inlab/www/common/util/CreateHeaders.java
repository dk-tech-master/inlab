package kr.inlab.www.common.util;

import org.springframework.http.HttpHeaders;

/**
 * 하나의 header 키에 여러 value 를 넣을 때 도움을 주는 클래스 ex) 사용자에게 삭제해야할 token 정보를 알려주는 헤더가 필요한데 clear-token 이라는 헤더의 키에 여러개의 값을 넣고
 * 싶으면 이 클래스를 사용하면 된다.
 */
public class CreateHeaders {

    public static final String CLEAR_TOKEN = "clear-token";
    public static final String PASSWORD_CHANGE_REQUIRED = "password-change-required";
    public static final String X_UNAUTHORIZED_USER = "X-Unauthorized-User";
    public static final String TRUE = "true";
    public static final String LOGIN_FAIL_BLOCK = "login-fail-block";
    public static final String LOGIN_FAIL_DELETE = "login-fail-delete";
    public static final String LOGIN_FAIL = "login-fail";
    public static final String USER_ID = "user-id";
    public static final String USER_NICKNAME = "nickname";

    public static HttpHeaders createClearTokenHeaders(String... tokenKeys) {
        HttpHeaders headers = new HttpHeaders();
        return doCreateHeaders(headers, CLEAR_TOKEN, tokenKeys);
    }

    public static HttpHeaders createClearTokenHeaders(HttpHeaders headers, String... tokenKeys) {
        return doCreateHeaders(headers, CLEAR_TOKEN, tokenKeys);
    }

    public static HttpHeaders addCustomHeaders(String headerName, String... headerValue) {
        HttpHeaders headers = new HttpHeaders();
        return doCreateHeaders(headers, headerName, headerValue);
    }

    public static HttpHeaders addCustomHeaders(String headerName) {
        HttpHeaders headers = new HttpHeaders();
        return doCreateHeaders(headers, headerName, TRUE);
    }

    public static HttpHeaders addCustomHeaders(HttpHeaders headers, String headerName, String... headerValue) {
        return doCreateHeaders(headers, headerName, headerValue);
    }

    public static HttpHeaders doCreateHeaders(HttpHeaders headers, String headerKey, String... tokenKeys) {
        headers.add(headerKey, String.join(",", tokenKeys));
        return headers;
    }
}
