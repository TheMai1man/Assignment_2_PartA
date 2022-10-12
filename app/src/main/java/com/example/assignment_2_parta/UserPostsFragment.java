package com.example.assignment_2_parta;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserPostsFragment extends Fragment
{
    private CommonData mViewModel;
    private PostList data;

    public UserPostsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recyclerview, ui, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);
        rv.setLayoutManager( new LinearLayoutManager( requireActivity(),
                                LinearLayoutManager.VERTICAL, false ) );

        String userId = String.valueOf(mViewModel.getSelectedUser().getUserId());

        UserPostsRetrievalThread postsThread = new UserPostsRetrievalThread(requireActivity(), mViewModel, userId );
        postsThread.start();

        mViewModel.postList.observe(requireActivity(), new Observer<PostList>()
        {
            @Override
            public void onChanged(PostList postList)
            {
                if(postList != null)
                {
                    if(data == null)
                    {
                        data = mViewModel.getPostList();
                    }

                    MyAdapter adapter = new MyAdapter(data);
                    rv.setAdapter(adapter);
                }
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {
        PostList data;

        public MyAdapter(PostList value)
        {
            data = value;
        }

        @Override
        public int getItemCount()
        {
            return data.size();
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