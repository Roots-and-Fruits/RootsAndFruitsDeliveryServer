package com.rootandfruit.server.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderRequestDto(
        @NotBlank(message = "발신자명은 필수입니다.")
        String senderName,
        @NotBlank(message = "발신자 전화번호는 필수입니다.")
        String senderPhone,
        boolean isMarketingConsent,
        @NotEmpty(message = "수신자 정보는 빈 값일 수 없습니다.")
        @Valid List<RecipientDto> recipientInfo
) {
}
