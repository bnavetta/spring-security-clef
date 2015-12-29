package com.bennavetta.clef.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

public interface UserServices
{
    UserDetails loadAuthentication(String clefId);

    UserDetails registerUser(UserInfo info);

    void setLoggedOutAt(String clefId, Instant loggedOutAt);

    Instant getLoggedOutAt(String clefId);
}