package com.bennavetta.clef.boot.security;

import com.bennavetta.clef.boot.StateStorage;
import com.bennavetta.clef.boot.UserServices;
import com.bennavetta.clef.boot.client.ClefClient;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ClefLoginConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractAuthenticationFilterConfigurer<H, ClefLoginConfigurer<H>, ClefAuthenticationProcessingFilter>
{
    public ClefLoginConfigurer()
    {
        super(new ClefAuthenticationProcessingFilter(),
              ClefAuthenticationProcessingFilter.DEFAULT_CALLBACK_PATH);
    }

    public ClefLoginConfigurer<H> clefClient(ClefClient clefClient)
    {
        getAuthenticationFilter().setClefClient(clefClient);
        return this;
    }

    public ClefLoginConfigurer<H> stateStorage(StateStorage stateStorage)
    {
        getAuthenticationFilter().setStateStorage(stateStorage);
        return this;
    }

    public ClefLoginConfigurer<H> userServices(UserServices userServices)
    {
        getAuthenticationFilter().setUserServices(userServices);
        return this;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl)
    {
        return new AntPathRequestMatcher(loginProcessingUrl);
    }
}
