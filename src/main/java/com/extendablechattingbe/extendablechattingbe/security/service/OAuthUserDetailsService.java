package com.extendablechattingbe.extendablechattingbe.security.service;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.MemberRole;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.security.dto.AuthMemberDTO;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthUserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String clientName = userRequest.getClientRegistration().getClientName();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String loginId = null;

        if(clientName.equals("Google")){
            loginId = oAuth2User.getAttribute("email");
        }

        Member member = saveFromSocialMember(loginId);
        AuthMemberDTO authMember = new AuthMemberDTO(
            member.getLoginId(),
            member.getPassword(),
            true,
            member.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList()),
            oAuth2User.getAttributes());

        authMember.addNickName(member.getNickname());
        return authMember;




    }


    private Member saveFromSocialMember(String loginId){
        Optional<Member> result = memberRepository.findByLoginIdAndFromSocial(loginId, true);
        if(result.isPresent()){
            return result.get();
        }

        Member member = Member.builder()
            .loginId(loginId)
            .nickname(loginId)
            .password(passwordEncoder.encode("1111"))
            .fromSocial(true)
            .build();

        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
        return member;
    }
}
