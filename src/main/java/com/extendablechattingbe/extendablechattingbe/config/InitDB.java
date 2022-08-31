package com.extendablechattingbe.extendablechattingbe.config;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.MemberRole;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final StartService startService;

    @PostConstruct
    public void init(){
        startService.initData();
    }



    @Component
    @Transactional
    @RequiredArgsConstructor
    static class StartService{

        private final MemberRepository memberRepository;
        private final PasswordEncoder passwordEncoder;

        public void initData(){
            IntStream.rangeClosed(1,100).forEach(i->{
                Member member = Member.builder()
                    .loginId("user" + i + "@zerock.org")
                    .nickname("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

                member.addMemberRole(MemberRole.USER);

                memberRepository.save(member);
            });
        }

    }
}
