package com.example.assignment_2_parta;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsersFragment extends Fragment
{
    private CommonData mViewModel;
    private UserList data;

    public UsersFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);
        data = mViewModel.getUserList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recyclerview, ui, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager( requireActivity(),
                                                LinearLayoutManager.VERTICAL, false));

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