package com.rootandfruit.server.dto;

import jakarta.persistence.Column;
import java.time.LocalDate;
import java.util.List;

public record OrderRequestDto(
        String senderName,
        String senderPhone,
        List<RecipientDto> recipientInfo
) {
}
