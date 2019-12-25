package com.usoft.sschool_pub.exception;

/**
 * @author Braycep
 * @date 2019/2/26 10:49
 */
public class BaseException extends RuntimeException {
    private ErrorCode errorCode;

    protected BaseException() {
    }

    public BaseException(ErrorCode errorCode) {
        this(errorCode.toString());
        this.errorCode = errorCode;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
