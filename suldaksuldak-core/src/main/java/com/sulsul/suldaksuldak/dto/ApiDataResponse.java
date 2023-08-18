package com.sulsul.suldaksuldak.dto;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ApiDataResponse<T> extends ApiErrorResponse{
    private final T data;
    private ApiDataResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    private ApiDataResponse(ErrorCode errorCode, T data) {
        super(errorCode == ErrorCode.OK, errorCode.getCode(), errorCode.getMessage());
        this.data = data;
    }

    private ApiDataResponse(ErrorCode errorCode, String errorMessage) {
        super(errorCode == ErrorCode.OK, errorCode.getCode(), errorMessage);
        this.data = null;
    }

    private ApiDataResponse(T data, String errorMessage) {
        super(true, ErrorCode.OK.getCode(), errorMessage);
        this.data = data;
    }

    public static <T> ApiDataResponse<T> of (T data) {
        return new ApiDataResponse<T>(data);
    }

    public static <T> ApiDataResponse<T> of (ErrorCode errorCode, T data) {
        return new ApiDataResponse<T>(errorCode, data);
    }

    public static <T> ApiDataResponse<T> of (ErrorCode errorCode, String message) {
        return new ApiDataResponse<T>(errorCode, message);
    }

    public static <T> ApiDataResponse<T> of (T data, String message) {
        return new ApiDataResponse<T>(data, message);
    }

    public static <T> ApiDataResponse<T> empty() {
        return new ApiDataResponse<>(null);
    }

    public static <T> ApiDataResponse<T> empty(ErrorCode errorCode) {
        return new ApiDataResponse<>(errorCode, null);
    }
}
