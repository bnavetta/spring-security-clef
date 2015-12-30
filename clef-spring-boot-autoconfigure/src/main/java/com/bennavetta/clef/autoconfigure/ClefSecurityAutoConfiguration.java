package com.bennavetta.clef.autoconfigure;

import com.bennavetta.clef.security.StateStorage;
import com.bennavetta.clef.security.UserServices;
import com.bennavetta.clef.security.authentication.ClefAuthenticationProvider;
import com.bennavetta.clef.security.client.ClefClient;
import com.bennavetta.clef.security.support.ClefHandshakeFilter;
import com.bennavetta.clef.security.support.ClefLogoutFilter;
import com.bennavetta.clef.security.support.ClefLogoutWebhookFilter;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import java.util.List;

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

    @Bean
    public ClefLogoutWebhookFilter clefLogoutWebhookFilter(ClefClient clefClient,
                                                           UserServices userServices)
    {
        ClefLogoutWebhookFilter logoutFilter = new ClefLogoutWebhookFilter();
        logoutFilter.setFilterProcessesUrl(settings.getLogoutWebhookUrl());
        logoutFilter.setClefClient(clefClient);
        logoutFilter.setUserServices(userServices);
        return logoutFilter;
    }

    @Bean
    @ConditionalOnMissingBean(ClefLogoutFilter.class) // can replace by defining a new bean
    public ClefLogoutFilter clefLogoutFilter(UserServices userServices, StateStorage stateStorage)
    {
        List<LogoutHandler> handlers = Lists.<LogoutHandler>newArrayList(new SecurityContextLogoutHandler());

        ClefLogoutFilter logoutFilter = new ClefLogoutFilter(settings.getLoggedOutUrl(), handlers);
        logoutFilter.setStateStorage(stateStorage);
        logoutFilter.setUserServices(userServices);

        return logoutFilter;
    }

    @Autowired
    public void configureGlobalAuth(AuthenticationManagerBuilder auth,
                                    ClefClient clefClient,
                                    UserServices userServices)
    {
        auth.authenticationProvider(new ClefAuthenticationProvider(clefClient, userServices));
    }
}
