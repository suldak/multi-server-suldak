package com.sulsul.suldaksuldak.controller.error;

import com.sulsul.suldaksuldak.constant.error.ErrorCode;
import com.sulsul.suldaksuldak.dto.ApiErrorResponse;
import com.sulsul.suldaksuldak.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(
                        ApiErrorResponse.of(
                                false,
                                errorCode,
                                e.getMessage()
                        ));
    }
}
