package com.example.assignment_2_parta;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

public class UserList
{
    private List<User> userList;

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
