package com.bennavetta.clef.boot;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;

/**
 * Provide access to Clef login state
 */
public interface StateStorage
{
    String getState(HttpServletRequest request);

    void clearState(HttpServletRequest request);

    void setState(HttpServletRequest request, String state);

    void setLoginTime(HttpServletRequest request, Instant loginTime);

    Instant getLoginTime(HttpServletRequest request);
}
