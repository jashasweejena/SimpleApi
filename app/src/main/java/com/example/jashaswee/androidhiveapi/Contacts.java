package com.example.jashaswee.androidhiveapi;

/**
 * Created by jashaswee on 31/3/18.
 */

public class Contacts {
    private String name;
    private String email;
    private String mobile;

    public Contacts(String name, String email, String mobile)
    {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }


}
