package com.extendablechattingbe.extendablechattingbe.repository;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByNickname(String nickname);
}
