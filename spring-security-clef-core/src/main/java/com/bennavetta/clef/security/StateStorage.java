package com.bennavetta.clef.security;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;

/**
 * Provide access to Clef login state
 */
public interface StateStorage
{
    /**
     * Load the random state value used to prevent CSRF attacks.
     * @param request the current request
     * @return the state value, or {@code null} if one was not set
     * @see #setState(HttpServletRequest, String)
     * @see #clearState(HttpServletRequest)
     */
    String getState(HttpServletRequest request);

    /**
     * Clear the stored state value.
     * @param request the current request
     * @see #getState(HttpServletRequest)
     * @see #setState(HttpServletRequest, String)
     */
    void clearState(HttpServletRequest request);

    /**
     * Store a state value in the current request. Generally, the value should be stored
     * in the HTTP session.
     * @param request the current request
     * @param state the new state value
     * @see #getState(HttpServletRequest)
     * @see #clearState(HttpServletRequest)
     */
    void setState(HttpServletRequest request, String state);

    void setLoginTime(HttpServletRequest request, Instant loginTime);

    Instant getLoginTime(HttpServletRequest request);
}
