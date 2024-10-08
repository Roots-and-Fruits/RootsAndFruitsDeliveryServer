package com.rootandfruit.server.api.controller.docs;

import com.rootandfruit.server.api.dto.ProductRequestDto;
import com.rootandfruit.server.api.dto.ProductResponseDto;
import com.rootandfruit.server.api.dto.ProductSailedResponseDto;
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

@Tag(name = "Product", description = "Product 관련 API")
public interface ProductControllerDocs {

    @Operation(summary = "모든 제품 조회", description = "전체 제품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 제품 조회 성공")
    })
    ResponseEntity<ProductResponseDto> getProducts();

    @Operation(summary = "판매 제품 조회", description = "판매 중인 제품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매 중인 제품 조회 성공")
    })
    ResponseEntity<ProductSailedResponseDto> getSailedProducts();

    @Operation(summary = "상품 판매 상태 변경", description = "상품의 판매 여부 상태를 변경합니다. 판매 O <-> 판매 X")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 판매 상태 변경 성공"),
            @ApiResponse(responseCode = "40401", description = "존재하지 않는 상품 ID")
    })
    ResponseEntity<Void> changeProduct(
            @Parameter(
                    description = "상태를 변경할 상품ID",
                    required = true
            )
            @PathVariable Long productId
    );

    @Operation(summary = "상품 등록", description = "새로운 상품을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "새로운 상품 등록 성공")
    })
    ResponseEntity<Void> postProduct(
            @Parameter(
                    description = "등록할 상품의 상세 정보",
                    required = true,
                    schema = @Schema(implementation = ProductRequestDto.class)
            )
            @RequestBody ProductRequestDto productRequestDto
    );

    @Operation(summary = "상품 삭제", description = "여러 상품을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "40401", description = "존재하지 않는 상품ID가 포함되어 있습니다.")
    })
    ResponseEntity<Void> deleteProduct(
            @Parameter(
                    description = "삭제할 상품들의 ID",
                    required = true,
                    schema = @Schema(type = "array", example = "[1, 2, 3]")
            )
            @RequestBody List<Long> productIds
    );
}