package com.bennavetta.clef.boot.autoconfigure;

import com.bennavetta.clef.boot.ClefProperties;
import com.bennavetta.clef.boot.StateProvider;
import com.bennavetta.clef.boot.StateStorage;
import com.bennavetta.clef.boot.UserServices;
import com.bennavetta.clef.boot.client.ClefClient;
import com.bennavetta.clef.boot.form.LoginHelper;
import com.bennavetta.clef.boot.security.ClefAuthenticationProcessingFilter;
import com.bennavetta.clef.boot.security.ClefLoginConfigurer;
import com.bennavetta.clef.boot.support.DefaultStateProvider;
import com.bennavetta.clef.boot.support.HttpSessionStateStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(SecurityAutoConfiguration.class)
@EnableConfigurationProperties(ClefProperties.class)
public class ClefAutoConfiguration
{
    @Autowired
    ClefProperties clefProperties;

    @Bean
    @ConditionalOnMissingBean(StateProvider.class)
    public DefaultStateProvider stateProvider()
    {
        return new DefaultStateProvider();
    }

    @Bean
    @ConditionalOnMissingBean(StateStorage.class)
    public HttpSessionStateStorage httpSessionStateStorage()
    {
        return new HttpSessionStateStorage();
    }

    @Bean
    @ConditionalOnMissingBean(ClefClient.class)
    public ClefClient clefClient()
    {
        return new ClefClient(clefProperties.getAppId(), clefProperties.getAppSecret());
    }

    @Bean
    @ConditionalOnMissingBean(LoginHelper.class)
    public LoginHelper loginHelper(StateProvider stateProvider, StateStorage stateStorage)
    {
        LoginHelper helper = new LoginHelper();
        helper.setAppId(clefProperties.getAppId());
        helper.setRedirectUrl(clefProperties.getRedirectUrl());
        helper.setStateProvider(stateProvider);
        helper.setStateStorage(stateStorage);
        helper.setFormColor(clefProperties.getFormColor());
        helper.setFormStyle(clefProperties.getFormStyle());

        return helper;
    }

    @Bean
    public ClefAuthenticationProcessingFilter clefFilter(ClefClient clefClient, UserServices userServices, StateStorage stateStorage)
    {
        ClefAuthenticationProcessingFilter filter = new ClefAuthenticationProcessingFilter(clefProperties.getRedirectUrl());
        filter.setClefClient(clefClient);
        filter.setStateStorage(stateStorage);
        filter.setUserServices(userServices);
        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        return filter;
    }

//    @Configuration
//    @Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
//    static class SecurityConfiguration extends WebSecurityConfigurerAdapter
//    {
//        @Autowired
//        ClefClient clefClient;
//
//        @Autowired
//        UserServices userServices;
//
//        @Autowired
//        StateStorage stateStorage;
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception
//        {
//            http.apply(new ClefLoginConfigurer<HttpSecurity>())
//                    .clefClient(clefClient)
//                    .stateStorage(stateStorage)
//                    .userServices(userServices);
//        }
//    }
}
