package com.example.assignment_2_parta;

import android.app.Activity;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class RemoteUtilities
{
    public static RemoteUtilities remoteUtilities = null;
    private Activity uiActivity;

    public RemoteUtilities(Activity uiActivity)
    {
        this.uiActivity = uiActivity;
    }

    //set uiActivity value
    public void setUiActivity(Activity uiActivity)
    {
        this.uiActivity = uiActivity;
    }

    public static RemoteUtilities getInstance(Activity uiActivity)
    {
        if(remoteUtilities == null)
        {
            remoteUtilities = new RemoteUtilities(uiActivity);
        }
        remoteUtilities.setUiActivity(uiActivity);
        return remoteUtilities;
    }

    //open connection to given URL
    public HttpsURLConnection openConnection(String urlString)
    {
        HttpsURLConnection conn = null;
        try
        {
            URL url = new URL(urlString);
            conn = (HttpsURLConnection) url.openConnection();
        }
        catch( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch( IOException e)
        {
            e.printStackTrace();
        }

        if( conn == null )
        {
            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(uiActivity, "Check Internet Connection", Toast.LENGTH_LONG).show();
                }
            });
        }

        return conn;
    }

    //check if connection okay
    public boolean isConnectionOkay(HttpsURLConnection conn)
    {
        try
        {
            if( conn.getResponseCode() == HttpsURLConnection.HTTP_OK )
            {
                return true;
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(uiActivity, "Problem with API endpoint", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return false;
    }

    //get String response
    public String getResponseString(HttpsURLConnection conn)
    {
        String data = null;
        try
        {
            InputStream input = conn.getInputStream();
            data = IOUtils.toString(input, StandardCharsets.UTF_8);
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

        return data;
    }
}
