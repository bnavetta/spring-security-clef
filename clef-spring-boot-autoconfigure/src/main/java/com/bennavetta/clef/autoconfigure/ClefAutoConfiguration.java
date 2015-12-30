package com.bennavetta.clef.autoconfigure;

import com.bennavetta.clef.security.StateProvider;
import com.bennavetta.clef.security.StateStorage;
import com.bennavetta.clef.security.client.ClefClient;
import com.bennavetta.clef.security.login.LoginHelper;
import com.bennavetta.clef.security.support.DefaultStateProvider;
import com.bennavetta.clef.security.support.HttpSessionStateStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(SecurityAutoConfiguration.class)
@EnableConfigurationProperties(ClefProperties.class)
@Import(ClefSecurityAutoConfiguration.class)
public class ClefAutoConfiguration
{
    @Autowired
    ClefProperties settings;

    @Bean
    @ConditionalOnMissingBean(StateProvider.class)
    public StateProvider stateProvider()
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
        return new ClefClient(settings.getAppId(), settings.getAppSecret());
    }

    @Bean
    @ConditionalOnMissingBean(LoginHelper.class)
    public LoginHelper loginHelper(StateProvider stateProvider, StateStorage stateStorage)
    {
        LoginHelper helper = new LoginHelper();
        helper.setAppId(settings.getAppId());
        helper.setRedirectUrl(settings.getRedirectUrl());
        helper.setStateProvider(stateProvider);
        helper.setStateStorage(stateStorage);
        return helper;
    }
}
