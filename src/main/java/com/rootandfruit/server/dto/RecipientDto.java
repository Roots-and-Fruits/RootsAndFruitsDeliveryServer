package com.rootandfruit.server.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record RecipientDto(
        @NotBlank(message = "수신자명은 필수입니다.")
        String recipientName,
        @NotBlank(message = "수신자 전화번호는 필수입니다.")
        String recipientPhone,
        @NotBlank(message = "수신자 주소는 필수입니다.")
        String recipientAddress,
        @NotBlank(message = "수신자 상세주소는 필수입니다.")
        String recipientAddressDetail,
        @NotBlank(message = "수신자 우편번호는 필수입니다.")
        String recipientPostCode,
        @NotEmpty(message = "상품정보는 빈 값일 수 없습니다.")
        @Valid List<ProductDto> productInfo,
        @NotNull(message = "배송날짜는 null값일 수 없습니다.")
        LocalDate deliveryDate
) {
}