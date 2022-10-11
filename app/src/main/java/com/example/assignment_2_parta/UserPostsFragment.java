package com.example.assignment_2_parta;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class UserPostsFragment extends Fragment
{
    private final String API_KEY = "";
    private CommonData mViewModel;
    private PostList data;

    public UserPostsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);
        data = new PostList();
        String userID = String.valueOf(mViewModel.getSelectedUser().getUserId());

        String urlString =
                Uri.parse("https://jsonplaceholder.typicode.com/posts/")
                        .buildUpon()
                        .appendQueryParameter("userId", userID)
                        .build().toString();
        HttpsURLConnection conn;

        try
        {
            URL url = new URL(urlString);
            try
            {
                conn = (HttpsURLConnection) url.openConnection();
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

                        //get list of posts by the user
                        JSONArray jPostList = new JSONArray(download);
                        //for each post
                        for(int ii = 0; ii < jPostList.length(); ii++)
                        {
                            JSONObject jPost = jPostList.getJSONObject(ii);

                            String title = jPost.getString("title");
                            String body = jPost.getString("body");

                            //add post to the PostList
                            data.add( new Post(title, body) );
                        }
                    }
                    catch(IOException e)
                    {
                        //download of the data failed
                    }
                }
                catch( JSONException e )
                {
                    //error during JSON handling
                }
                finally
                {
                    //end connection
                    conn.disconnect();
                }
            }
            catch(MalformedURLException e)
            {
                //URL malformed
            }
        }
        catch(IOException e)
        {
            //IO error
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recyclerview, ui, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager( new LinearLayoutManager( requireActivity(),
                                LinearLayoutManager.VERTICAL, false ) );

        MyAdapter adapter = new MyAdapter(data);
        rv.setAdapter(adapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        PostList data;

        public MyAdapter(PostList data)
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
            Post post = data.get(index);
            vh.bind(post);
        }
    }

    private static class MyDataVHolder extends RecyclerView.ViewHolder
    {
        private final TextView titleText, bodyText;

        public MyDataVHolder(LayoutInflater li, ViewGroup parent)
        {
            super( li.inflate(R.layout.post_selection, parent, false) );
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            bodyText = (TextView) itemView.findViewById(R.id.bodyText);
        }

        public void bind(Post data)
        {
            titleText.setText(data.getTitle());
            bodyText.setText(data.getBody());
        }
    }
}