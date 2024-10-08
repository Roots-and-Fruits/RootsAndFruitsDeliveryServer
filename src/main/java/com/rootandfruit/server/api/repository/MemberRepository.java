package com.rootandfruit.server.api.repository;

import com.rootandfruit.server.api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
