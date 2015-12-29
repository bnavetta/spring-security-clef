package com.bennavetta.clef.security.authentication;

import com.bennavetta.clef.security.UserInfo;
import com.bennavetta.clef.security.UserServices;
import com.bennavetta.clef.security.client.ClefClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.google.common.base.Preconditions.checkNotNull;

public class ClefAuthenticationProvider implements AuthenticationProvider, InitializingBean
{
    private static final Logger log = LoggerFactory.getLogger(ClefAuthenticationProvider.class);

    private ClefClient clefClient;
    private UserServices userServices;

    private boolean autoRegister = false;

    public ClefAuthenticationProvider() {}

    public ClefAuthenticationProvider(ClefClient clefClient, UserServices userServices)
    {
        this.clefClient = clefClient;
        this.userServices = userServices;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        ClefCodeAuthenticationToken auth = (ClefCodeAuthenticationToken) authentication;
        String code = auth.getCode();

        log.debug("Try to perform OAuth2 handshake with Clef");
        String accessToken = clefClient.handshake(code);

        log.debug("Try to retrieve user information from Clef");
        UserInfo info = clefClient.getUserInfo(accessToken);
        log.debug("Successfully retrieved information for {}", info.getClefId());

        UserDetails userDetails = userServices.loadAuthentication(info.getClefId());
        if (userDetails == null)
        {
            if (autoRegister)
            {
                log.debug("Registering new user from Clef info {}", info);
                userDetails = userServices.registerUser(info);
            }
            else
            {
                throw new UsernameNotFoundException("No user with Clef ID " + info.getClefId());
            }
        }

        additionalAuthenticationChecks(userDetails);

        // TODO: include Clef ID?
        ClefAuthentication responseAuth = new ClefAuthentication(userDetails, accessToken);
        responseAuth.setAuthenticated(true);
        return responseAuth;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return ClefCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        checkNotNull(clefClient, "clefClient must be specified");
        checkNotNull(userServices, "userServices must be specified");
    }

    public ClefClient getClefClient()
    {
        return clefClient;
    }

    public void setClefClient(ClefClient clefClient)
    {
        this.clefClient = clefClient;
    }

    public UserServices getUserServices()
    {
        return userServices;
    }

    public void setUserServices(UserServices userServices)
    {
        this.userServices = userServices;
    }

    public boolean isAutoRegister()
    {
        return autoRegister;
    }

    public void setAutoRegister(boolean autoRegister)
    {
        this.autoRegister = autoRegister;
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails) throws AuthenticationException
    {

    }
}
