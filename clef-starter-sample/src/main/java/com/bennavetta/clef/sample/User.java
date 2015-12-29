package com.bennavetta.clef.sample;

import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Objects;

public class User
{
    @Id
    private String id;
    private String email;
    private String clefId;
    private Instant loggedOutAt;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getClefId()
    {
        return clefId;
    }

    public void setClefId(String clefId)
    {
        this.clefId = clefId;
    }

    public Instant getLoggedOutAt()
    {
        return loggedOutAt;
    }

    public void setLoggedOutAt(Instant loggedOutAt)
    {
        this.loggedOutAt = loggedOutAt;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("clefId", clefId)
                .add("loggedOutAt", loggedOutAt)
                .toString();
    }
}
