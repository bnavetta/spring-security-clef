package com.bennavetta.clef.autoconfigure;

import com.bennavetta.clef.security.StateStorage;
import com.bennavetta.clef.security.UserServices;
import com.bennavetta.clef.security.authentication.ClefAuthenticationProvider;
import com.bennavetta.clef.security.client.ClefClient;
import com.bennavetta.clef.security.support.ClefHandshakeFilter;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
public class ClefSecurityAutoConfiguration
{
    @Autowired
    ClefProperties settings;

    @Bean
    public ClefHandshakeFilter clefHandshakeFilter(AuthenticationManager authenticationManager,
                                                   StateStorage stateStorage)
    {
        ClefHandshakeFilter filter = new ClefHandshakeFilter(settings.getRedirectUrl());
        filter.setStateStorage(stateStorage);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        return filter;
    }

    @Autowired
    public void configureGlobalAuth(AuthenticationManagerBuilder auth,
                                    ClefClient clefClient,
                                    UserServices userServices)
    {
        auth.authenticationProvider(new ClefAuthenticationProvider(clefClient, userServices));
    }
}
