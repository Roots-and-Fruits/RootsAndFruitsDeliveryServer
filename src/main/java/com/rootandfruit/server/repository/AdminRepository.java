package com.rootandfruit.server.repository;

import com.rootandfruit.server.domain.Admin;
import com.rootandfruit.server.domain.OrderMetaData;
import com.rootandfruit.server.global.exception.CustomException;
import com.rootandfruit.server.global.exception.ErrorType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByUsername(String username);

    default Admin findAdminByUsernameOrThrow(String username) {
        return findAdminByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_ADMIN_ERROR));
    }
}
