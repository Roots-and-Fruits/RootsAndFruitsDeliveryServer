package com.rootandfruit.server.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Delivery", description = "배송 관련 API")
public interface DeliveryControllerDocs {

    @Operation(summary = "배송 상태를 '발송완료'로 변경", description = "전달된 배송 정보 ID 목록을 통해 해당 배송 상태를 '발송완료'으로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배송 상태 변경 성공"),
            @ApiResponse(responseCode = "40405", description = "존재하지 않는 배송 정보 ID")
    })
    ResponseEntity<Void> statusToShipped(
            @Parameter(
                    description = "배송 상태를 변경할 배송 정보 ID 목록",
                    required = true,
                    schema = @Schema(type = "array", example = "[1, 2, 3]")
            )
            @RequestBody List<Long> deliveryInfoIds
    );

    @Operation(summary = "최대 배송 가능일 조회", description = "현재 설정된 최대 배송 가능일을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최대 배송 가능일 조회 성공")
    })
    ResponseEntity<Integer> getAllowedDeliveryDay();

    @Operation(summary = "최대 배송 가능일 변경", description = "새로운 최대 배송 가능일을 설정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최대 배송 가능일 변경 성공")
    })
    ResponseEntity<Void> changeDay(
            @Parameter(
                    description = "변경할 최대 배송 가능일 (예: 7일, 14일)",
                    required = true,
                    schema = @Schema(type = "int", example = "10")
            )
            @PathVariable int allowedDeliveryDays
    );
}