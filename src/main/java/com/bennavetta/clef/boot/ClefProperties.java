package com.bennavetta.clef.boot;

import com.bennavetta.clef.boot.form.FormColor;
import com.bennavetta.clef.boot.form.Style;

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
     * The color to use on Clef forms.
     */
    private FormColor formColor = FormColor.BLUE;

    /**
     * The style to use for Clef forms.
     */
    private Style formStyle = Style.FLAT;

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

    public FormColor getFormColor()
    {
        return formColor;
    }

    public void setFormColor(FormColor formColor)
    {
        this.formColor = formColor;
    }

    public void setFormColor(String formColor)
    {
        this.formColor = FormColor.valueOf(formColor.toUpperCase());
    }

    public Style getFormStyle()
    {
        return formStyle;
    }

    public void setFormStyle(Style formStyle)
    {
        this.formStyle = formStyle;
    }

    public void setFormStyle(String formStyle)
    {
        this.formStyle = Style.valueOf(formStyle.toUpperCase());
    }
}
