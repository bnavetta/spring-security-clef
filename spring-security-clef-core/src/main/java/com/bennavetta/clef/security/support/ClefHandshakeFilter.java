package com.bennavetta.clef.security.support;

import com.bennavetta.clef.security.StateStorage;
import com.bennavetta.clef.security.authentication.ClefCodeAuthenticationToken;

import com.google.common.base.Objects;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter for the Clef handshake.
 */
public class ClefHandshakeFilter extends AbstractAuthenticationProcessingFilter
{
    public static final String DEFAULT_CALLBACK_URL = "/clef/callback";

    private Clock clock = Clock.systemUTC();

    private StateStorage stateStorage;

    public ClefHandshakeFilter()
    {
        this(DEFAULT_CALLBACK_URL);
    }

    public ClefHandshakeFilter(String url)
    {
        super(url);
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

            ClefCodeAuthenticationToken authRequest = new ClefCodeAuthenticationToken(code);
            return getAuthenticationManager().authenticate(authRequest);
        }
        else
        {
            throw new BadCredentialsException("Invalid login state");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException
    {
        stateStorage.setLoginTime(request, Instant.now(clock));
        super.successfulAuthentication(request, response, chain, authResult);
    }

    public StateStorage getStateStorage()
    {
        return stateStorage;
    }

    public void setStateStorage(StateStorage stateStorage)
    {
        this.stateStorage = stateStorage;
    }

    public Clock getClock()
    {
        return clock;
    }

    public void setClock(Clock clock)
    {
        this.clock = clock;
    }
}
