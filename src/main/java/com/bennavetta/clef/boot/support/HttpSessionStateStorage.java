package com.bennavetta.clef.boot.support;

import com.bennavetta.clef.boot.StateStorage;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionStateStorage implements StateStorage
{
    public static final String STATE_KEY = HttpSessionStateStorage.class.getName() + ".state";
    public static final String LOGIN_TIME_KEY = HttpSessionStateStorage.class.getName() + ".loginTime";

    @Override
    public String getState(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            return (String) session.getAttribute(STATE_KEY);
        }
        return null;
    }

    @Override
    public void clearState(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            session.removeAttribute(STATE_KEY);
        }
    }

    @Override
    public void setState(HttpServletRequest request, String state)
    {
        request.getSession().setAttribute(STATE_KEY, state);
    }

    @Override
    public void setLoginTime(HttpServletRequest request, Instant loginTime)
    {
        request.getSession().setAttribute(LOGIN_TIME_KEY, loginTime);
    }

    @Override
    public Instant getLoginTime(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session != null)
        {
            return (Instant) session.getAttribute(LOGIN_TIME_KEY);
        }
        return null;
    }
}
