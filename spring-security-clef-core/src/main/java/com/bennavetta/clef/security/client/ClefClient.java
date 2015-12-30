package com.bennavetta.clef.security.client;

import com.bennavetta.clef.security.UserInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ClefClient
{
    private static final String AUTHORIZE_URL = "https://clef.io/api/v1/authorize";
    private static final String INFO_URL = "https://clef.io/api/v1/info?access_token={0}";
    private static final String LOGOUT_URL = "https://clef.io/api/v1/logout";

    private final String appId;
    private final String appSecret;
    private final RestTemplate restTemplate;

    public ClefClient(String appId, String appSecret, RestTemplate restTemplate)
    {
        this.appId = appId;
        this.appSecret = appSecret;
        this.restTemplate = restTemplate;
    }

    public ClefClient(String appId, String appSecret)
    {
        this(appId, appSecret, new RestTemplate());
    }

    /**
     * Perform a login handshake.
     * @param code the OAuth code
     * @return the access token
     */
    public String handshake(String code) throws ClefClientException
    {
        MultiValueMap<String, String> handshake = new LinkedMultiValueMap<String, String>();
        handshake.add("code", code);
        handshake.add("app_id", appId);
        handshake.add("app_secret", appSecret);

        HandshakeResponse response = restTemplate.postForObject(AUTHORIZE_URL, handshake, HandshakeResponse.class);
        if (response.success)
        {
            return response.accessToken;
        }
        else
        {
            throw new ClefClientException(response.error);
        }
    }

    public UserInfo getUserInfo(String accessToken) throws ClefClientException
    {
        InfoResponse response = restTemplate.getForObject(INFO_URL, InfoResponse.class, accessToken);
        if (response.success)
        {
            return response.info;
        }
        else
        {
            throw new ClefClientException(response.error);
        }
    }

    public String exchangeLogoutToken(String logoutToken) throws ClefClientException
    {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
        data.add("logout_token", logoutToken);
        data.add("app_id", appId);
        data.add("app_secret", appSecret);

        LogoutResponse response = restTemplate.postForObject(LOGOUT_URL, data, LogoutResponse.class);
        if (response.success)
        {
            return response.clefId;
        }
        else
        {
            throw new ClefClientException(response.error);
        }
    }

    private static class HandshakeResponse
    {
        @JsonProperty
        boolean success;

        @JsonProperty("access_token")
        String accessToken;

        @JsonProperty
        String error;
    }

    private static class InfoResponse
    {
        @JsonProperty
        boolean success;

        @JsonProperty
        UserInfo info;

        @JsonProperty
        String error;
    }

    private static class LogoutResponse
    {
        @JsonProperty
        boolean success;

        @JsonProperty("clef_id")
        String clefId;

        @JsonProperty
        String error;
    }
}