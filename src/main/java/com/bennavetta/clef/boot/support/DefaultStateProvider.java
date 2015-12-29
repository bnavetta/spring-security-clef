package com.bennavetta.clef.boot.support;

import com.bennavetta.clef.boot.StateProvider;

import com.google.common.io.BaseEncoding;

import java.security.SecureRandom;

public class DefaultStateProvider implements StateProvider
{
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generateState()
    {
        byte[] buffer = new byte[64];
        random.nextBytes(buffer);
        return BaseEncoding.base64Url().omitPadding().encode(buffer);
    }
}
