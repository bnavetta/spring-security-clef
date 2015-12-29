package com.bennavetta.clef.security.login;

import com.bennavetta.clef.security.StateProvider;
import com.bennavetta.clef.security.StateStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;

/**
 * Helper for controllers implementing Clef login and registration.
 */
public class LoginHelper
{
    private static final Logger log = LoggerFactory.getLogger(LoginHelper.class);

    private StateProvider stateProvider;
    private StateStorage stateStorage;

    private String appId;
    private String redirectUrl;

    public URI getRedirectUri(HttpServletRequest request)
    {
        return ServletUriComponentsBuilder.fromServletMapping(request)
                .path(redirectUrl)
                .build()
                .toUri();
    }

    public LoginState prepareForLogin(HttpServletRequest request)
    {
        log.trace("Preparing for login from {}", request);
        String state = stateProvider.generateState();
        stateStorage.setState(request, state);
        URI redirectUri = getRedirectUri(request);
        return new LoginState(redirectUri, appId, state);
    }

    public StateProvider getStateProvider()
    {
        return stateProvider;
    }

    public void setStateProvider(StateProvider stateProvider)
    {
        this.stateProvider = stateProvider;
    }

    public StateStorage getStateStorage()
    {
        return stateStorage;
    }

    public void setStateStorage(StateStorage stateStorage)
    {
        this.stateStorage = stateStorage;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getRedirectUrl()
    {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }
}
