package com.extendablechattingbe.extendablechattingbe.repository;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @EntityGraph(attributePaths = {"roleSet"},type = EntityGraphType.LOAD)
    @Query("select m from Member m where m.loginId = :loginId and m.fromSocial = :social")
    Optional<Member> findByLoginIdAndFromSocial(@Param("loginId")String loginId,@Param("social")boolean social);


    @EntityGraph(attributePaths = {"roleSet"},type = EntityGraphType.LOAD)
    Optional<Member> findByLoginId(String LoginId);

}
