package com.rootandfruit.server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.persistence.Id;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    private Admin(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public static Admin createAdmin(final String username, final String rawPassword) {
        // BCrypt를 사용해 비밀번호를 인코딩
        String encodedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        return Admin.builder()
                .username(username)
                .password(encodedPassword)
                .build();
    }
}
