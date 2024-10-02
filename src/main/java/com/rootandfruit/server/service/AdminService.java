package com.rootandfruit.server.service;

import com.rootandfruit.server.domain.Admin;
import com.rootandfruit.server.dto.AdminAuthenticateRequestDto;
import com.rootandfruit.server.dto.AdminCreateRequestDto;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import com.rootandfruit.server.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public void createAdmin(AdminCreateRequestDto adminCreateRequestDto) {
        Admin admin = Admin.createAdmin(adminCreateRequestDto.username(), adminCreateRequestDto.password());
        adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public void authenticateAdmin(AdminAuthenticateRequestDto adminAuthenticateRequestDto) {
        Admin admin = adminRepository.findAdminByUsernameOrThrow(adminAuthenticateRequestDto.username());
        if (!BCrypt.checkpw(adminAuthenticateRequestDto.password(), admin.getPassword())) {
            throw new CustomException(ErrorType.INVALID_ADMIN_PASSWORD);
        }
    }
}
