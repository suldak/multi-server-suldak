package com.sulsul.suldaksuldak.controller.error;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiDataResponse;
import com.sulsul.suldaksuldak.dto.ApiErrorResponse;
import com.sulsul.suldaksuldak.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(GeneralException e) {
        ErrorCode errorCode  = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiErrorResponse.of(
                                false,
                                errorCode,
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(ErrorCode.INTERNAL_ERROR.getHttpStatus())
                .body(
                        ApiErrorResponse.of(
                                false,
                                ErrorCode.INTERNAL_ERROR,
                                e.getMessage()
                        )
                );
    }
}
