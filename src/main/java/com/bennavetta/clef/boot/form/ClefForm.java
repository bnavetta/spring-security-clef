package com.bennavetta.clef.boot.form;

/**
 * State for a Clef login form
 */
public class ClefForm
{
    private String appId;
    private String redirectUrl;
    private FormColor color;
    private Style style;
    private Phrasing phrasing;
    private String state;

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getRedirectUrl()
    {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }

    public FormColor getColor()
    {
        return color;
    }

    public void setColor(FormColor color)
    {
        this.color = color;
    }

    public Style getStyle()
    {
        return style;
    }

    public void setStyle(Style style)
    {
        this.style = style;
    }

    public Phrasing getPhrasing()
    {
        return phrasing;
    }

    public void setPhrasing(Phrasing phrasing)
    {
        this.phrasing = phrasing;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String toHtml()
    {
        return "<script src=\"https://clef.io/v3/clef.js\" "
                + "data-app-id=\"" + appId + "\" "
                + "data-state=\"" + state + "\" "
                + "data-redirect-url=\"" + redirectUrl + "\" "
                + "data-color=\"" + color.name().toLowerCase() + "\" "
                + "data-style=\"" + style.name().toLowerCase() + "\" "
                + "data-type=\"" + phrasing.name().toLowerCase() + "\" "
                + "class=\"clef-button\"></script>";
    }
}
