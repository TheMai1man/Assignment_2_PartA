package com.example.assignment_2_parta;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class UserListRetrievalThread extends Thread
{
    private final String baseUrl;
    private final RemoteUtilities remoteUtilities;
    private final SearchResponseViewModel sViewModel;
    private final CommonData mViewModel;

    public UserListRetrievalThread(Activity uiActivity, SearchResponseViewModel sViewModel, CommonData mViewModel)
    {
        baseUrl = "https://jsonplaceholder.typicode.com/users/";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.sViewModel = sViewModel;
        this.mViewModel = mViewModel;
    }

    public void run()
    {
        UserList data = new UserList();
        HttpsURLConnection conn = remoteUtilities.openConnection(this.baseUrl);
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

                try
                {
                    //get list of users from json placeholder
                    JSONArray jUserList =  new JSONArray(response);
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
                catch( JSONException e )
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
