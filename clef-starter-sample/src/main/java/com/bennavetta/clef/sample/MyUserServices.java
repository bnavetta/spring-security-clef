package com.bennavetta.clef.sample;

import com.bennavetta.clef.boot.UserServices;
import com.bennavetta.clef.boot.client.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MyUserServices implements UserServices
{
    private final UserRepository userRepository;

    @Autowired
    public MyUserServices(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication loadAuthentication(String clefId)
    {
        User user = userRepository.findByClefId(clefId);
        if (user != null)
        {
            return new UserAuthenticationToken(user);
        }
        return null;
    }

    @Override
    public Authentication registerUser(UserInfo info)
    {
        User user = new User();
        user.setClefId(info.getClefId());
        user.setEmail(info.getEmail());
        return new UserAuthenticationToken(userRepository.save(user));
    }

    @Override
    public void setLoggedOutAt(String clefId, Instant loggedOutAt)
    {
        User user = userRepository.findByClefId(clefId);
        user.setLoggedOutAt(loggedOutAt);
        userRepository.save(user);
    }

    @Override
    public Instant getLoggedOutAt(String clefId)
    {
        return userRepository.findByClefId(clefId).getLoggedOutAt();
    }
}
