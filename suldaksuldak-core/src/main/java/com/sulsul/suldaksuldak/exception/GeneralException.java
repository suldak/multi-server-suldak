package com.sulsul.suldaksuldak.exception;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.constant.error.ErrorMessage;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;
    private Object body;

    public GeneralException() {
        super(ErrorCode.INTERNAL_ERROR.getMessage());
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(message));
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message, Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(message), cause);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(cause));
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, ErrorMessage message) {
        super(message.getMessage());
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, Object body) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.body = body;
    }

    public GeneralException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }
}
