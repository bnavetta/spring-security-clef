package com.bennavetta.clef.autoconfigure;

import com.bennavetta.clef.security.StateStorage;
import com.bennavetta.clef.security.UserServices;
import com.bennavetta.clef.security.authentication.ClefAuthenticationProvider;
import com.bennavetta.clef.security.client.ClefClient;
import com.bennavetta.clef.security.support.ClefHandshakeFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
public class ClefSecurityAutoConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    ClefClient clefClient;

    @Autowired
    UserServices userServices;

    @Autowired
    StateStorage stateStorage;

    @Autowired
    ClefProperties settings;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        ClefHandshakeFilter filter = new ClefHandshakeFilter(settings.getRedirectUrl());
        filter.setStateStorage(stateStorage);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());

        http.authenticationProvider(new ClefAuthenticationProvider(clefClient, userServices))
                .addFilter(filter);
    }
}
