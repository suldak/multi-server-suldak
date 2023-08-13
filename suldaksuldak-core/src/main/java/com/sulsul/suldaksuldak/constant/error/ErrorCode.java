package com.sulsul.suldaksuldak.constant.error;

import com.sulsul.suldaksuldak.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    OK(200, HttpStatus.OK, "OK"),
    BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND(10003, HttpStatus.NOT_FOUND, "요청을 찾지 못했습니다."),
    INTERNAL_ERROR(20000, HttpStatus.INTERNAL_SERVER_ERROR, "서버측 오류"),
    DATA_ACCESS_ERROR(20002, HttpStatus.INTERNAL_SERVER_ERROR, "데이터 조회에 실패했습니다."),
    ;

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public static ErrorCode valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) { throw new GeneralException("HttpStatus is null."); }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) { return ErrorCode.BAD_REQUEST; }
                    else if (httpStatus.is5xxServerError()) { return ErrorCode.INTERNAL_ERROR; }
                    else { return ErrorCode.OK; }
                });
    }

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}
