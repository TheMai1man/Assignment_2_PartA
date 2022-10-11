package com.example.assignment_2_parta;

import android.app.Activity;
import android.net.Uri;

import javax.net.ssl.HttpsURLConnection;

public class UserPostsRetrievalThread extends Thread
{
    private final String userId;
    private final String baseUrl;
    private final RemoteUtilities remoteUtilities;
    private final SearchResponseViewModel viewModel;

    public UserPostsRetrievalThread(String userId, Activity uiActivity, SearchResponseViewModel viewModel)
    {
        this.userId = userId;
        baseUrl = "https://jsonplaceholder.typicode.com/posts/";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.viewModel = viewModel;
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

                viewModel.setResponse(response);
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
