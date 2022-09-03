package com.extendablechattingbe.extendablechattingbe.security.dto;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private String loginId;
    private String nickname;
    private boolean fromSocial;
    private String password;

    private Map<String,Object> attr;

    public AuthMemberDTO(String username,String password,boolean fromSocial,
        Collection<? extends GrantedAuthority> authorities, Map<String,Object> attr){
        this(username,password,fromSocial,authorities);
        this.attr = attr;
    }

    public AuthMemberDTO(String username,String password,boolean fromSocial,
        Collection<? extends GrantedAuthority> authorities){
        super(username,password,authorities);
        this.loginId = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return nickname;
    }

    public void addNickName(String nickname){
        this.nickname = nickname;
    }

}
