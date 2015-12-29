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
}
