package com.example.assignment_2_parta;

import java.util.ArrayList;
import java.util.List;

public class PostList
{
    private List<Post> postList = new ArrayList<>();

    public PostList() {}

    public int size()
    {
        return postList.size();
    }

    public void add(Post value)
    {
        postList.add(value);
    }

    public Post get(int ii)
    {
        return postList.get(ii);
    }
}
