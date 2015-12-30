package com.bennavetta.clef.security.support;

import com.bennavetta.clef.security.UserServices;
import com.bennavetta.clef.security.client.ClefClient;
import com.bennavetta.clef.security.client.ClefClientException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Filter implementing the Clef logout webhook.
 */
public class ClefLogoutWebhookFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(ClefLogoutWebhookFilter.class);

    private RequestMatcher logoutRequestMatcher;
    private UserServices userServices;
    private ClefClient clefClient;
    private Clock clock = Clock.systemUTC();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        if (logoutRequestMatcher.matches(request))
        {
            String logoutToken = request.getParameter("logout_token");
            log.trace("Logout webhook call for token {}", logoutToken);

            try
            {
                String clefId = clefClient.exchangeLogoutToken(logoutToken);
                log.trace("Marking user {} as logged out", clefId);
                userServices.setLoggedOutAt(clefId, Instant.now(clock));
            }
            catch (ClefClientException e)
            {
                log.error("Logout webhook error", e);
            }

            return;
        }

        filterChain.doFilter(request, response);
    }

    public void setLogoutRequestMatcher(RequestMatcher logoutRequestMatcher)
    {
        checkNotNull(logoutRequestMatcher, "logoutRequestMatcher cannot be null");
        this.logoutRequestMatcher = logoutRequestMatcher;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl)
    {
        this.logoutRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
    }

    public void setClefClient(ClefClient clefClient)
    {
        this.clefClient = clefClient;
    }

    public void setClock(Clock clock)
    {
        this.clock = clock;
    }

    public void setUserServices(UserServices userServices)
    {
        this.userServices = userServices;
    }
}
