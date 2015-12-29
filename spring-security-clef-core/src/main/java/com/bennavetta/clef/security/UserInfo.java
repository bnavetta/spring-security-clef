package com.bennavetta.clef.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class UserInfo
{
    String clefId;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;

    @JsonProperty("id")
    public String getClefId()
    {
        return clefId;
    }

    public void setClefId(String clefId)
    {
        this.clefId = clefId;
    }

    @JsonProperty("first_name")
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @JsonProperty("last_name")
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @JsonProperty("phone_number")
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("email")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(clefId, userInfo.clefId) &&
                Objects.equals(firstName, userInfo.firstName) &&
                Objects.equals(lastName, userInfo.lastName) &&
                Objects.equals(phoneNumber, userInfo.phoneNumber) &&
                Objects.equals(email, userInfo.email);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(clefId, firstName, lastName, phoneNumber, email);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("clefId", clefId)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("phoneNumber", phoneNumber)
                .add("email", email)
                .toString();
    }
}
