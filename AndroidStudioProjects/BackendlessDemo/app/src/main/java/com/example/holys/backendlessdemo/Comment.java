package com.example.holys.backendlessdemo;

/**
 * Created by holys on 2/26/2016.
 */
public class Comment {

    private String message;
    private String authorEmail;

    public Comment(){}

    public Comment(String message, String authorEmail)
    {
        this.message = message;
        this.authorEmail = authorEmail;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setAuthorEmail(String message)
    {
        this.authorEmail = authorEmail;
    }

    public String getAuthorEmail()
    {
        return this.authorEmail;
    }
}
