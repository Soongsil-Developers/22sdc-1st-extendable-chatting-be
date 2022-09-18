package com.extendablechattingbe.extendablechattingbe.repository;

import com.extendablechattingbe.extendablechattingbe.domain.Member;

import java.time.LocalDateTime;
import java.util.Optional;
import com.extendablechattingbe.extendablechattingbe.domain.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByNickname(String nickname);

    @Query(value = "select min(m.createdDate) from Message m where m.room = :room and m.member = :member")
    Optional<LocalDateTime> findEnterDate(@Param("room") Room room, @Param("member") Member member);

}
