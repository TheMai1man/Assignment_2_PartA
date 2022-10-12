package com.example.assignment_2_parta;

import android.app.Activity;
import android.net.Uri;

import javax.net.ssl.HttpsURLConnection;

public class UserPostsRetrievalThread extends Thread
{
    private final String userId;
    private final String baseUrl;
    private final RemoteUtilities remoteUtilities;

    public UserPostsRetrievalThread(String userId, Activity uiActivity)
    {
        this.userId = userId;
        baseUrl = "https://jsonplaceholder.typicode.com/posts/";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
    }

    public void run()
    {
        String endpoint = getSearchEndpoint();
        HttpsURLConnection conn = remoteUtilities.openConnection(endpoint);
        if( conn != null )
        {
            if(remoteUtilities.isConnectionOkay(conn) )
            {
                String response = remoteUtilities.getResponseString(conn);
                conn.disconnect();
                try
                {
                    Thread.sleep(3000);
                }
                catch( Exception e ) {}
            }
        }
    }

    private String getSearchEndpoint()
    {
        Uri.Builder url = Uri.parse(this.baseUrl).buildUpon()
                .appendQueryParameter("userId", this.userId);

        return url.build().toString();
    }

}
