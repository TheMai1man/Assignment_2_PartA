package com.example.assignment_2_parta;

public class User
{
    private final int userId;
    private final String userName;
    private final String phone;
    private final String email;
    private final String address;
    private final String website;
    private final String details;

    public User(int id, String username, String phone, String email, String address, String website, String details)
    {
        this.userId = id;
        this.userName = username;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.website = website;
        this.details = details;
    }

    public String getUserName()
    {
        return this.userName;
    }
    public int getUserId()
    {
        return this.userId;
    }
    public String getPhone()
    {
        return this.phone;
    }
    public String getEmail()
    {
        return this.email;
    }
    public String getAddress()
    {
        return this.address;
    }
    public String getWebsite()
    {
        return this.website;
    }
    public String getDetails()
    {
        return this.details;
    }
}
