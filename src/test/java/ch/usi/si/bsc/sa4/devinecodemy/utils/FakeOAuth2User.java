package ch.usi.si.bsc.sa4.devinecodemy.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class FakeOAuth2User implements OAuth2User {

    private final String name;
    private final OAuth2AuthenticationToken oAuth2AuthenticationToken;

    public FakeOAuth2User(String name) {
        this.name = name;
        this.oAuth2AuthenticationToken = new OAuth2AuthenticationToken(this,Collections.emptyList(),this.name);
    }

    public OAuth2AuthenticationToken getOAuth2AuthenticationToken() {
        return oAuth2AuthenticationToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return name;
    }
}
