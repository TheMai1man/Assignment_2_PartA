package com.example.assignment_2_parta;

import android.app.Activity;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class UserPostsRetrievalThread extends Thread
{
    private String userId;
    private String baseUrl;
    private RemoteUtilities remoteUtilities;
    private CommonData mViewModel;

    public UserPostsRetrievalThread(Activity uiActivity, CommonData mViewModel, String userId)
    {
        this.userId = userId;
        baseUrl = "https://jsonplaceholder.typicode.com/posts/";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.mViewModel = mViewModel;
    }

    public void run()
    {
        String download = getUserPostsFromUrl();
        PostList data = new PostList();

        try
        {
            JSONArray jUserPosts = new JSONArray(download);

            for(int ii = 0; ii < jUserPosts.length(); ii++)
            {
                JSONObject jPost = jUserPosts.getJSONObject(ii);

                String title = jPost.getString("title");
                String body = jPost.getString("body");
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    private String getUserPostsFromUrl()
    {
        String download = null;
        Uri.Builder url = Uri.parse(baseUrl).buildUpon();
        url.appendQueryParameter("userId", userId);
        String urlString = url.build().toString();
        HttpsURLConnection conn = remoteUtilities.openConnection(conn);
        if(conn!=null)
        {
            if(remoteUtilities.isConnectionOkay(conn))
            {
                download = remoteUtilities.getResponseString(conn);
                conn.disconnect();
            }
        }
        return download;
    }
}
