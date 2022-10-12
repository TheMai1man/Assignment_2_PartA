package com.example.assignment_2_parta;

import android.app.Activity;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class UserPostsRetrievalThread extends Thread
{
    private final String userId;
    private final String baseUrl;
    private final RemoteUtilities remoteUtilities;
    private final CommonData mViewModel;
    private final Activity uiActivity;

    public UserPostsRetrievalThread(Activity uiActivity, CommonData mViewModel, String userId)
    {
        this.userId = userId;
        baseUrl = "https://jsonplaceholder.typicode.com/posts/";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.mViewModel = mViewModel;
        this.uiActivity = uiActivity;
    }

    public void run()
    {
        String download = getUserPostsFromUrl();
        PostList data = new PostList();

        try
        {
            //get list of posts from json placeholder
            JSONArray jUserPosts = new JSONArray(download);

            //for each post
            for(int ii = 0; ii < jUserPosts.length(); ii++)
            {
                //get details of the post
                JSONObject jPost = jUserPosts.getJSONObject(ii);

                String title = jPost.getString("title");
                String body = jPost.getString("body");

                //add post to list
                data.add(new Post(title, body));
            }

            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    mViewModel.setPostList(data);
                }
            });

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        try
        {
            Thread.sleep(3000);
        }
        catch(Exception e)
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
        HttpsURLConnection conn = remoteUtilities.openConnection(urlString);
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
