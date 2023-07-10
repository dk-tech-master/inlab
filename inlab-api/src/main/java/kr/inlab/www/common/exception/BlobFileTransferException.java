package kr.inlab.www.common.exception;

import org.springframework.http.HttpStatus;

public class BlobFileTransferException extends InlabException {

    private static final String MESSAGE = "BLOB 파일을 저장하는데 실패했습니다.";

    public BlobFileTransferException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
