package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class FileConvertException extends InlabException {

    private static final String MESSAGE = "파일 변환을 실패했습니다.";

    public FileConvertException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
