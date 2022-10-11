package com.example.assignment_2_parta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private CommonData mViewModel;
    private SearchResponseViewModel sViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserListRetrievalThread usersThread = new UserListRetrievalThread(MainActivity.this, sViewModel, mViewModel);
        usersThread.start();

        mViewModel.userList.observe(MainActivity.this, new Observer<UserList>()
        {
            @Override
            public void onChanged(UserList userList)
            {
                //Users Fragment loads on start
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.frameLayout, UsersFragment.class, null)
                        .commit();
            }
        });


    }
}