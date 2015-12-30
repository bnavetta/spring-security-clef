package com.bennavetta.clef.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "clef")
public class ClefProperties
{
    /**
     * The Clef app ID from the developer panel.
     */
    private String appId;

    /**
     * The Clef app secret from the developer panel.
     */
    private String appSecret;

    /**
     * The full URL to direct Clef at.
     */
    private String redirectUrl = "/clef/callback";

    /**
     * The URL to register the logout webhook at.
     */
    private String logoutWebhookUrl = "/clef/logout";

    /**
     * The URL to redirect to if the user is logged out.
     */
    private String loggedOutUrl = "";

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppSecret()
    {
        return appSecret;
    }

    public void setAppSecret(String appSecret)
    {
        this.appSecret = appSecret;
    }

    public String getRedirectUrl()
    {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }

    public String getLogoutWebhookUrl()
    {
        return logoutWebhookUrl;
    }

    public void setLogoutWebhookUrl(String logoutWebhookUrl)
    {
        this.logoutWebhookUrl = logoutWebhookUrl;
    }

    public String getLoggedOutUrl()
    {
        return loggedOutUrl;
    }

    public void setLoggedOutUrl(String loggedOutUrl)
    {
        this.loggedOutUrl = loggedOutUrl;
    }
}
