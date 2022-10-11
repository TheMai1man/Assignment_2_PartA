package com.example.assignment_2_parta;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UserDetailsFragment extends Fragment
{
    private CommonData mViewModel;
    TextView username, phone, email, address, website, details;
    Button postsButton;

    public UserDetailsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle savedInstanceState)
    {
        mViewModel = new ViewModelProvider(requireActivity()).get(CommonData.class);

        username = (TextView) ui.findViewById(R.id.usernameText);
        phone = (TextView) ui.findViewById(R.id.phoneText);
        email = (TextView) ui.findViewById(R.id.emailText);
        address = (TextView) ui.findViewById(R.id.addressText);
        website = (TextView) ui.findViewById(R.id.websiteText);
        details = (TextView) ui.findViewById(R.id.detailsText);

        User user = mViewModel.getSelectedUser();

        username.setText( user.getUserName() );
        phone.setText( user.getPhone() );
        email.setText( user.getEmail() );
        address.setText( user.getAddress() );
        website.setText( user.getWebsite() );
        details.setText( user.getDetails() );

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, ui, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        postsButton = (Button) view.findViewById(R.id.postsButton);
        postsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.frameLayout, UserPostsFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}