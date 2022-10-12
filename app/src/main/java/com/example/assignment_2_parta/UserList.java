package com.example.assignment_2_parta;

import java.util.ArrayList;
import java.util.List;

public class UserList
{
    private List<User> userList = new ArrayList<>();

    public UserList() {}

    public int size()
    {
        return userList.size();
    }

    public void add(User value)
    {
        userList.add(value);
    }

    public User get(int i)
    {
        return userList.get(i);
    }
}
