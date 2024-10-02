package com.rootandfruit.server.controller;

import com.rootandfruit.server.controller.docs.AdminControllerDocs;
import com.rootandfruit.server.dto.AdminAuthenticateRequestDto;
import com.rootandfruit.server.dto.AdminCreateRequestDto;
import com.rootandfruit.server.service.AdminService;
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
