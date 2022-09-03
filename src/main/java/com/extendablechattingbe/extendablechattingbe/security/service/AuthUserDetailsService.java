package com.extendablechattingbe.extendablechattingbe.security.service;

import static com.extendablechattingbe.extendablechattingbe.error.ErrorCode.MEMBER_NOT_FOUND_ERROR;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.error.exception.NotFoundException;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.security.dto.AuthMemberDTO;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = repository.findByLoginIdAndFromSocial(username, false);
        if(!findMember.isPresent()){
            throw new NotFoundException(MEMBER_NOT_FOUND_ERROR);
        }
        Member member = findMember.get();

        AuthMemberDTO AuthMember = new AuthMemberDTO(
            member.getLoginId(),
            member.getPassword(),
            member.isFromSocial(),
            member.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet())
        );

        AuthMember.addNickName(member.getNickname());
        return AuthMember;
    }
}
