package com.bennavetta.clef.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

public class ClefCodeAuthenticationToken extends AbstractAuthenticationToken
{
    private final String code;
    private final Object principal;

    public ClefCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities, String code)
    {
        super(authorities);
        this.principal = principal;
        this.code = code;
        super.setAuthenticated(true);
    }

    public ClefCodeAuthenticationToken(String code)
    {
        super(null);
        this.code = code;
        this.principal = null;
    }

    @Override
    public Object getCredentials()
    {
        return null;
    }

    @Override
    public Object getPrincipal()
    {
        return principal;
    }

    public String getCode()
    {
        return code;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClefCodeAuthenticationToken that = (ClefCodeAuthenticationToken) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), code);
    }
}
