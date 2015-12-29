package com.bennavetta.clef.security.login;

import com.google.common.base.MoreObjects;

import java.net.URI;
import java.util.Objects;

/**
 * Information needed to construct a Clef login button.
 */
public class LoginState
{
    private final URI redirectUri;
    private final String appId;
    private final String state;

    public LoginState(URI redirectUri, String appId, String state)
    {
        this.redirectUri = redirectUri;
        this.appId = appId;
        this.state = state;
    }

    public URI getRedirectUri()
    {
        return redirectUri;
    }

    public String getAppId()
    {
        return appId;
    }

    public String getState()
    {
        return state;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginState that = (LoginState) o;
        return Objects.equals(redirectUri, that.redirectUri) &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(redirectUri, appId, state);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("redirectUri", redirectUri)
                .add("appId", appId)
                .add("state", state)
                .toString();
    }
}
