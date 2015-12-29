package com.bennavetta.clef.sample;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;

public class UserAuthenticationToken extends AbstractAuthenticationToken
{
    private final User user;

    public UserAuthenticationToken(User user)
    {
        super(Collections.<GrantedAuthority>emptyList());
        this.user = user;
        setDetails(user);
    }

    @Override
    public Object getCredentials()
    {
        return user.getClefId();
    }

    @Override
    public Object getPrincipal()
    {
        return user.getEmail();
    }
}
