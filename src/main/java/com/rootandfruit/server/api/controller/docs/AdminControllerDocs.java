package com.rootandfruit.server.api.controller.docs;

import com.rootandfruit.server.api.dto.AdminAuthenticateRequestDto;
import com.rootandfruit.server.api.dto.AdminAuthenticateResponseDto;
import com.rootandfruit.server.api.dto.AdminCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Admin", description = "Admin 관련 API")
public interface AdminControllerDocs {

    @Operation(summary = "관리자 계정 생성", description = "관리자 계정을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자 계성 생성 성공")
    })
    ResponseEntity<Void> createAdmin(
            @Parameter(
                    description = "생성할 관리자 계정 정보",
                    required = true,
                    schema = @Schema(implementation = AdminCreateRequestDto.class)
            )
            @RequestBody AdminCreateRequestDto adminCreateRequestDto
    );

    @Operation(summary = "관리자 계정 로그인", description = "관리자 계정으로 로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자 로그인 성공"),
            @ApiResponse(responseCode = "40406", description = "관리자 아이디 불일치"),
            @ApiResponse(responseCode = "40008", description = "관리자 비밀번호 불일치")
    })
    ResponseEntity<AdminAuthenticateResponseDto> authenticateAdmin(
            @Parameter(
                    description = "관리자 아이디, 비밀번호",
                    required = true,
                    schema = @Schema(implementation = AdminAuthenticateRequestDto.class)
            )
            @RequestBody AdminAuthenticateRequestDto adminAuthenticateRequestDto
    );
}
