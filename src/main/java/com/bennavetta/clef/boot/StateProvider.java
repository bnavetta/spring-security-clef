package com.bennavetta.clef.boot;

/**
 * Provider for the random state used in Clef logins.
 */
@FunctionalInterface
public interface StateProvider
{
    String generateState();
}
