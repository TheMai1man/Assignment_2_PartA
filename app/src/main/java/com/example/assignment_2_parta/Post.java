package com.example.assignment_2_parta;

public class Post
{
    private final String title;
    private final String body;

    public Post(String title, String body)
    {
        this.title = title;
        this.body = body;
    }

    public String getTitle()
    {
        return this.title;
    }
    public String getBody()
    {
        return this.body;
    }
}
