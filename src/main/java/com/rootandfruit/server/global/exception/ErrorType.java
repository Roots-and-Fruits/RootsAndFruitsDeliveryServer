package com.rootandfruit.server.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {

    /**
     * 400 BAD REQUEST
     */
    // 표준 오류
    REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "40001", "잘못된 요청입니다."),
    INVALID_TYPE_ERROR(HttpStatus.BAD_REQUEST, "40002", "잘못된 타입이 입력되었습니다."),
    INVALID_MISSING_HEADER_ERROR(HttpStatus.BAD_REQUEST, "40003", "요청에 필요한 헤더값이 존재하지 않습니다."),
    INVALID_HTTP_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "40004", "요청 형식이 허용된 형식과 다릅니다."),
    INVALID_HTTP_METHOD_ERROR(HttpStatus.BAD_REQUEST, "40005", "지원되지 않는 HTTP method 요청입니다."),
    INVALID_DELIVERY_STATUS_ERROR(HttpStatus.BAD_REQUEST, "40006", "잘못된 배송상태가 입력되었습니다."),
    ALREADY_DELETED_PRODUCT(HttpStatus.BAD_REQUEST, "40007", "이미 삭제된 상품입니다."),

    /**
     * 404 NOT FOUND
     */
    NOT_FOUND_PRODUCT_ERROR(HttpStatus.NOT_FOUND, "40401", "존재하지 않는 상품입니다."),
    NOT_FOUND_ORDER_META_DATA_ERROR(HttpStatus.NOT_FOUND, "40402", "주문 메타데이터가 존재하지 않습니다."),
    NOT_FOUND_ORDER_ERROR(HttpStatus.NOT_FOUND, "40403", "주문번호에 대한 주문내역이 존재하지 않습니다."),
    NOT_FOUND_DELIVERY_INFO_ERROR(HttpStatus.NOT_FOUND, "40404", "주문번호에 대한 배송정보가 존재하지 않습니다."),
    NOT_FOUND_DELIVERY_ERROR(HttpStatus.NOT_FOUND, "40405", "존재하지 않는 베송정보입니다."),

    /**
     * 500 INTERNAL SERVER ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50001", "알 수 없는 서버 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}