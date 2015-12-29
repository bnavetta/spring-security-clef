package com.bennavetta.clef.boot.form;

import com.bennavetta.clef.boot.StateProvider;
import com.bennavetta.clef.boot.StateStorage;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper to manage logging in
 */
public class LoginHelper
{
    private StateProvider stateProvider;
    private StateStorage stateStorage;

    private String appId;
    private String redirectUrl;

    private FormColor formColor;
    private Style formStyle;

    public ClefForm prepareLogin(HttpServletRequest request, Phrasing loginType)
    {
        String state = stateProvider.generateState();
        stateStorage.setState(request, state);

        ClefForm form = new ClefForm();
        form.setAppId(appId);
        form.setState(state);

        String redirectUri = ServletUriComponentsBuilder.fromServletMapping(request)
                .path(redirectUrl).build()
                .toUriString();
        form.setRedirectUrl(redirectUri);

        form.setColor(formColor);
        form.setStyle(formStyle);
        form.setPhrasing(loginType);
        return form;
    }

    public StateProvider getStateProvider()
    {
        return stateProvider;
    }

    public void setStateProvider(StateProvider stateProvider)
    {
        this.stateProvider = stateProvider;
    }

    public StateStorage getStateStorage()
    {
        return stateStorage;
    }

    public void setStateStorage(StateStorage stateStorage)
    {
        this.stateStorage = stateStorage;
    }

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

    public FormColor getFormColor()
    {
        return formColor;
    }

    public void setFormColor(FormColor formColor)
    {
        this.formColor = formColor;
    }

    public Style getFormStyle()
    {
        return formStyle;
    }

    public void setFormStyle(Style formStyle)
    {
        this.formStyle = formStyle;
    }
}
