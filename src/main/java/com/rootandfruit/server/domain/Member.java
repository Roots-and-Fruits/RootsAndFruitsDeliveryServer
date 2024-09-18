package com.rootandfruit.server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "is_marketing_consent", nullable = false)
    private boolean isMarketingConsent;

    @Builder
    private Member(
            final String name,
            final String phone,
            final boolean isMarketingConsent
    ) {
        this.name = name;
        this.phone = phone;
        this.isMarketingConsent = isMarketingConsent;
    }

    public static Member createMember(
            final String name,
            final String phone,
            final boolean isMarketingConsent
    ) {
        return Member.builder()
                .name(name)
                .phone(phone)
                .isMarketingConsent(isMarketingConsent)
                .build();
    }
}