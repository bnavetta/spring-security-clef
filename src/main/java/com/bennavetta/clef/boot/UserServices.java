package com.bennavetta.clef.boot;

import com.bennavetta.clef.boot.client.UserInfo;

import org.springframework.security.core.Authentication;

import java.time.Instant;

public interface UserServices
{
    Authentication loadAuthentication(String clefId);

    Authentication registerUser(UserInfo info);

    void setLoggedOutAt(String clefId, Instant loggedOutAt);

    Instant getLoggedOutAt(String clefId);
}
