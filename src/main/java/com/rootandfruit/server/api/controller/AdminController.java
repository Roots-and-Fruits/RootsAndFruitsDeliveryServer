package com.rootandfruit.server.api.controller;

import com.rootandfruit.server.api.controller.docs.AdminControllerDocs;
import com.rootandfruit.server.api.dto.AdminAuthenticateRequestDto;
import com.rootandfruit.server.api.dto.AdminCreateRequestDto;
import com.rootandfruit.server.api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController implements AdminControllerDocs {
    private final AdminService adminService;

    @PostMapping("")
    public ResponseEntity<Void> createAdmin(
            @RequestBody AdminCreateRequestDto adminCreateRequestDto
    ) {
        adminService.createAdmin(adminCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Void> authenticateAdmin(
            @RequestBody AdminAuthenticateRequestDto adminAuthenticateRequestDto
            ) {
        adminService.authenticateAdmin(adminAuthenticateRequestDto);
        return ResponseEntity.ok().build();
    }
}
