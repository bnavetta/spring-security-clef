package com.bennavetta.clef.boot.security;

import com.bennavetta.clef.boot.StateStorage;
import com.bennavetta.clef.boot.UserServices;
import com.bennavetta.clef.boot.client.ClefClient;
import com.bennavetta.clef.boot.client.ClefClientException;
import com.bennavetta.clef.boot.client.UserInfo;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter for the Clef handshake.
 */
public class ClefAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter
{
    private static final Logger log = LoggerFactory.getLogger(ClefAuthenticationProcessingFilter.class);

    public static final String DEFAULT_CALLBACK_PATH = "/clef/callback";

    private ClefClient clefClient;
    private Clock clock = Clock.systemUTC();

    private StateStorage stateStorage;
    private UserServices userServices;

    public ClefAuthenticationProcessingFilter()
    {
        this(DEFAULT_CALLBACK_PATH);
    }

    public ClefAuthenticationProcessingFilter(String url)
    {
        super(url);
        setAuthenticationManager(new NoOpAuthenticationManager());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException
    {
        String state = request.getParameter("state");
        String expectedState = stateStorage.getState(request);

        if (Objects.equal(state, expectedState))
        {
            stateStorage.clearState(request);

            String code = request.getParameter("code");
            try
            {
                log.trace("Performing handshake with {}", code);
                String accessToken = clefClient.handshake(code);
                log.trace("Got access token {}", accessToken);

                UserInfo info = clefClient.getUserInfo(accessToken);

                log.debug("Attempting to log in user with Clef id {}", info.getClefId());

                Authentication auth = userServices.loadAuthentication(info.getClefId());
                stateStorage.setLoginTime(request, Instant.now(clock));
                if (auth == null)
                {
                    log.debug("Registering user from {}", info);
                    auth = userServices.registerUser(info);
                }

                auth.setAuthenticated(true);

                return auth;
            }
            catch (ClefClientException e)
            {
                throw new BadCredentialsException("Clef error: " + e.getMessage(), e);
            }
        }
        else
        {
            throw new BadCredentialsException("Invalid login state");
        }
    }

    public ClefClient getClefClient()
    {
        return clefClient;
    }

    public void setClefClient(ClefClient clefClient)
    {
        this.clefClient = clefClient;
    }

    public StateStorage getStateStorage()
    {
        return stateStorage;
    }

    public void setStateStorage(StateStorage stateStorage)
    {
        this.stateStorage = stateStorage;
    }

    public UserServices getUserServices()
    {
        return userServices;
    }

    public void setUserServices(UserServices userServices)
    {
        this.userServices = userServices;
    }

    public Clock getClock()
    {
        return clock;
    }

    public void setClock(Clock clock)
    {
        this.clock = clock;
    }

    private static final class NoOpAuthenticationManager implements AuthenticationManager
    {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException
        {
            throw new UnsupportedOperationException("Should not be called");
        }
    }
}
