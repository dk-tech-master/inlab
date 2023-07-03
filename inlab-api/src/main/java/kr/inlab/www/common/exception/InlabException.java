package kr.inlab.www.common.exception;

public abstract class InlabException extends RuntimeException {

    public InlabException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
