package com.bennavetta.clef.security;

/**
 * Provider for the random state used in Clef logins.
 */
@FunctionalInterface
public interface StateProvider
{
    String generateState();
}
