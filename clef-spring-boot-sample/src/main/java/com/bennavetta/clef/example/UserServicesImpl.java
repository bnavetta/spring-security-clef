package com.bennavetta.clef.example;

import com.bennavetta.clef.example.model.User;
import com.bennavetta.clef.example.model.UserRepository;
import com.bennavetta.clef.security.UserInfo;
import com.bennavetta.clef.security.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserServicesImpl implements UserServices
{
    private final UserRepository userRepository;

    @Autowired
    public UserServicesImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadAuthentication(String clefId)
    {
        return userRepository.findByClefId(clefId);
    }

    @Override
    public UserDetails registerUser(UserInfo info)
    {
        User user = new User();
        user.setClefId(info.getClefId());
        user.setEmail(info.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void setLoggedOutAt(String clefId, Instant loggedOutAt)
    {
        User user = userRepository.findByClefId(clefId);
        if (user != null)
        {
            user.setLoggedOutAt(loggedOutAt);
            userRepository.save(user);
        }
    }

    @Override
    public Instant getLoggedOutAt(String clefId)
    {
        User user = userRepository.findByClefId(clefId);
        if (user != null)
        {
            return user.getLoggedOutAt();
        }
        return null;
    }
}
