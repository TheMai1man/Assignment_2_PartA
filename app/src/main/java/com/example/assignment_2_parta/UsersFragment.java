package com.example.assignment_2_parta;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

public class UsersFragment extends Fragment
{
    private CommonData mViewModel;
    private UserList data;
    private final String API_KEY = "";

    public UsersFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String urlString =
                Uri.parse("https://jsonplaceholder.typicode.com/users/")
                        .buildUpon()
                        .appendQueryParameter("api_key", API_KEY)
                        .appendQueryParameter("format", "json")
                        .appendQueryParameter("nojsoncallback", "1")
                        .appendQueryParameter("extras", "url_s")
                        .build().toString();

        URL url;
        try
        {
            url = new URL(urlString);
        }
        catch(MalformedURLException e)
        {
            //something
        }

        HttpsURLConnection conn;
        try
        {
            conn = (HttpsURLConnection) url.openConnection();
        }
        catch(IOException e)
        {
            //something
        }

        try
        {
            //check status
            if(conn.getResponseCode() != HttpsURLConnection.HTTP_OK)
            {
                //error
            }

            //download data
            String download;

            try
            {
                download = IOUtils.toString( conn.getInputStream(), StandardCharsets.UTF_8);
            }
            catch(IOException e)
            {
                //something
            }

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

        }
        catch( JSONException e )
        {
            //do something
        }
        finally
        {
            //end connection
            conn.disconnect();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recyclerview, ui, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager( requireActivity(),
                                                LinearLayoutManager.VERTICAL, false));

        if(data == null)
        {
            data = new UserList();
        }

        MyAdapter adapter = new MyAdapter(data);
        rv.setAdapter(adapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        UserList data;

        public MyAdapter(UserList data)
        {
            this.data = data;
        }

        @Override
        public int getItemCount()
        {
            return this.data.size();
        }

        @NonNull
        @Override
        public MyDataVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(requireActivity());
            return new MyDataVHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MyDataVHolder vh, int index)
        {
            User user = this.data.get(index);
            vh.bind(user);

            vh.userView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mViewModel.setSelectedUser(user);
                    FragmentManager fm = requireActivity().getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.frameLayout, UserDetailsFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    private static class MyDataVHolder extends RecyclerView.ViewHolder
    {
        private final TextView textView;
        private final ConstraintLayout userView;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super( li.inflate(R.layout.user_selection, parent, false) );
            textView = (TextView) itemView.findViewById(R.id.usernameText);
            userView = (ConstraintLayout) itemView.findViewById(R.id.userSelection);
        }

        public void bind(User data)
        {
            textView.setText(data.getUserName());
        }
    }
}