package com.bennavetta.clef.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class ClefAuthentication extends AbstractAuthenticationToken
{
    private final String accessToken;
    private final UserDetails userDetails;

    public ClefAuthentication(UserDetails userDetails, String accessToken)
    {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        this.accessToken = accessToken;
    }

    @Override
    public Object getCredentials()
    {
        return null;
    }

    @Override
    public Object getPrincipal()
    {
        return userDetails;
    }

    public String getAccessToken()
    {
        return accessToken;
    }
}
