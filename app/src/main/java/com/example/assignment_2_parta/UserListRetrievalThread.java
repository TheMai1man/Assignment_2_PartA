package com.example.assignment_2_parta;

import android.app.Activity;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;

public class UserListRetrievalThread extends Thread
{
    private String baseUrl;
    private RemoteUtilities remoteUtilities;
    private CommonData mViewModel;

    public UserListRetrievalThread(Activity uiActivity, CommonData mViewModel)
    {
        baseUrl = "https://jsonplaceholder.typicode.com/users/";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.mViewModel = mViewModel;
    }

    public void run()
    {
        String download = getUserListFromUrl();
        UserList data = new UserList();

        try
        {
            //get list of users from json placeholder
            JSONArray jUserList =  new JSONArray(download);

            //for each user
            for(int ii = 0; ii < jUserList.length(); ii++)
            {
                //get details of the user
                JSONObject jUser = jUserList.getJSONObject(ii);
                JSONObject jAddress = jUser.getJSONObject("address");
                JSONObject jDetails = jUser.getJSONObject("company");

                String username = jUser.getString("username");
                String phone = jUser.getString("phone");
                String email = jUser.getString("email");
                String website = jUser.getString("website");
                String address = jAddress.getString("street") + ", \t" +
                        jAddress.getString("suite") + ", \n" +
                        jAddress.getString("city") + ", \t" +
                        jAddress.getString("zipcode");
                String details = jDetails.getString("name") + "\n" +
                        jDetails.getString("catchPhrase") + "\n" +
                        jDetails.getString("bs") + "\n";
                int id = jUser.getInt("id");

                //add user to UserList in CommonData using details gathered
                data.add( new User(id, username, phone, email, address, website, details) );
            }

            mViewModel.setUserList(data);
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

    private String getUserListFromUrl()
    {
        String download = null;
        Uri.Builder url = Uri.parse(baseUrl).buildUpon();
        String urlString = url.build().toString();
        HttpsURLConnection conn = remoteUtilities.openConnection(urlString);
        if(conn != null)
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
