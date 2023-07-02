package kr.inlab.www.common.util;

import java.util.Arrays;
import org.springframework.http.HttpHeaders;

public class ClearTokenHeaders {
    public static final String CLEAR_TOKEN = "Clear_Token";

    public static HttpHeaders createHeaders(String... tokenKeys){
        HttpHeaders headers = new HttpHeaders();
        headers.add(CLEAR_TOKEN, String.join(",", tokenKeys));
        return headers;
    }
}
