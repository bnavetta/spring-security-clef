package com.bennavetta.clef.security.support;

import com.bennavetta.clef.security.StateStorage;
import com.bennavetta.clef.security.UserServices;
import com.bennavetta.clef.security.authentication.ClefAuthentication;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Filter to automatically log out users if Clef marks them as logged out.
 */
public class ClefLogoutFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(ClefLogoutFilter.class);

    private UserServices userServices;
    private StateStorage stateStorage;

    private final List<LogoutHandler> handlers;
    private final LogoutSuccessHandler logoutSuccessHandler;

    public ClefLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, List<LogoutHandler> handlers)
    {
        checkArgument(!handlers.isEmpty(), "LogoutHandlers are required");
        this.handlers = handlers;

        checkNotNull(logoutSuccessHandler, "logoutSuccessHandler cannot be null");
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    public ClefLogoutFilter(String logoutSuccessUrl, List<LogoutHandler> handlers)
    {
        checkArgument(!handlers.isEmpty(), "LogoutHandlers are required");
        this.handlers = handlers;

        checkArgument(logoutSuccessUrl.isEmpty() || UrlUtils.isValidRedirectUrl(logoutSuccessUrl),
                      "%s isn't a valid redirect URL", logoutSuccessUrl);
        SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        if (!logoutSuccessUrl.isEmpty())
        {
            urlLogoutSuccessHandler.setDefaultTargetUrl(logoutSuccessUrl);
        }
        this.logoutSuccessHandler = urlLogoutSuccessHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && ClefAuthentication.class.isAssignableFrom(auth.getClass()))
        {
            ClefAuthentication clefAuth = (ClefAuthentication) auth;
            log.debug("Checking if user {} is logged out", clefAuth);

            Instant loggedOutAt = userServices.getLoggedOutAt(clefAuth.getClefId());
            Instant loggedInAt = stateStorage.getLoginTime(request);

            if (loggedOutAt != null && loggedInAt.compareTo(loggedOutAt) < 0)
            {
                log.debug("User {} has been logged out", clefAuth);

                for (LogoutHandler handler : handlers)
                {
                    handler.logout(request, response, auth);
                }

                logoutSuccessHandler.onLogoutSuccess(request, response, auth);

                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    public void setStateStorage(StateStorage stateStorage)
    {
        this.stateStorage = stateStorage;
    }

    public void setUserServices(UserServices userServices)
    {
        this.userServices = userServices;
    }
}
