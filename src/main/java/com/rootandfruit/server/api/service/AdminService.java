package com.rootandfruit.server.api.service;

import com.rootandfruit.server.api.domain.Admin;
import com.rootandfruit.server.api.dto.AdminAuthenticateRequestDto;
import com.rootandfruit.server.api.dto.AdminAuthenticateResponseDto;
import com.rootandfruit.server.api.dto.AdminCreateRequestDto;
import com.rootandfruit.server.global.auth.jwt.JwtUtil;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import com.rootandfruit.server.api.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void createAdmin(AdminCreateRequestDto adminCreateRequestDto) {
        Admin admin = Admin.createAdmin(adminCreateRequestDto.username(), adminCreateRequestDto.password());
        adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public AdminAuthenticateResponseDto authenticateAdmin(AdminAuthenticateRequestDto adminAuthenticateRequestDto) {
        Admin admin = adminRepository.findAdminByUsernameOrThrow(adminAuthenticateRequestDto.username());
        if (!BCrypt.checkpw(adminAuthenticateRequestDto.password(), admin.getPassword())) {
            throw new CustomException(ErrorType.INVALID_ADMIN_PASSWORD);
        }
        return AdminAuthenticateResponseDto.of(jwtUtil.generateToken(adminAuthenticateRequestDto.username(), "ROLE_ADMIN"));
    }
}
