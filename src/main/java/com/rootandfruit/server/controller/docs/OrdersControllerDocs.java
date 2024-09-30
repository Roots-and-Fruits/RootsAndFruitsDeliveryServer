package com.rootandfruit.server.controller.docs;

import com.rootandfruit.server.dto.OrderNumberResponseDto;
import com.rootandfruit.server.dto.OrderRequestDto;
import com.rootandfruit.server.dto.OrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Order", description = "Order 관련 API")
public interface OrdersControllerDocs {

    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 생성 성공")
    })
    ResponseEntity<Integer> order(
            @Parameter(
                    description = "생성할 주문의 상세 정보",
                    required = true,
                    schema = @Schema(implementation = OrderRequestDto.class)
            )
            @RequestBody OrderRequestDto orderRequestDto
    );

    @Operation(summary = "주문 번호로 조회", description = "주문번호를 이용해 조회하여, 결제에 사용됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 상세 조회 성공"),
            @ApiResponse(responseCode = "40403", description = "주문 번호에 해당하는 주문을 찾을 수 없음")
    })
    ResponseEntity<OrderNumberResponseDto> order(
            @Parameter(
                    description = "조회할 주문의 주문번호",
                    required = true
            )
            @PathVariable int orderNumber
    );

    @Operation(summary = "주문 검색", description = "주문 수신일, 배송일, 상품 이름, 배송 상태를 기준으로 주문을 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 검색 성공")
    })
    ResponseEntity<OrderResponseDto> searchFieldPosition(
            @Parameter(
                    description = "주문이 접수된 날짜",
                    required = false,
                    schema = @Schema(type = "string", format = "date", example = "2024-09-01")
            )
            @RequestParam(required = false) final LocalDate orderReceivedDate,
            @Parameter(
                    description = "배송 예정 날짜 (사실 출발 날짜임)",
                    required = false,
                    schema = @Schema(type = "string", format = "date", example = "2024-09-10")
            )
            @RequestParam(required = false) final LocalDate deliveryDate,
            @Parameter(
                    description = "주문 상품 이름",
                    required = false,
                    schema = @Schema(type = "string", example = "귤 몇kg")
            )
            @RequestParam(required = false) final String productName,
            @Parameter(
                    description = "배송 상태 (예: '접수완료', '결제완료', '결제취소', '발송완료')",
                    required = false,
                    schema = @Schema(type = "string", example = "접수완료")
            )
            @RequestParam(required = false) final String deliveryStatus
    );

    @Operation(summary = "주문 결제 처리", description = "주문 번호를 이용해 주문을 결제 처리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 결제 성공"),
            @ApiResponse(responseCode = "40404", description = "주문 번호에 해당하는 배송내역을 찾을 수 없음")
    })
    ResponseEntity<Void> orderPay(
            @Parameter(
                    description = "결제할 주문의 번호",
                    required = true
            )
            @PathVariable int orderNumber
    );

    @Operation(summary = "주문 취소", description = "주문 번호를 이용해 결제를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 취소 성공"),
            @ApiResponse(responseCode = "40404", description = "주문 번호에 해당하는 배송내역을 찾을 수 없음")
    })
    ResponseEntity<Void> orderCancel(
            @Parameter(
                    description = "취소할 주문의 번호",
                    required = true
            )
            @PathVariable int orderNumber
    );
}