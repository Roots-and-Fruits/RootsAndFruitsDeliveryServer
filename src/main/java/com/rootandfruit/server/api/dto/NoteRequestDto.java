package com.rootandfruit.server.api.dto;

public record NoteRequestDto(
        Long orderId,
        String note
) {

}
