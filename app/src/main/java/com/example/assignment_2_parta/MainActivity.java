package com.example.assignment_2_parta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private CommonData mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = new ViewModelProvider(MainActivity.this).get(CommonData.class);

        UserListRetrievalThread usersThread = new UserListRetrievalThread(MainActivity.this, mViewModel);
        usersThread.start();

        mViewModel.userList.observe(MainActivity.this, new Observer<UserList>()
        {
            @Override
            public void onChanged(UserList userList)
            {
                if(userList != null)
                {
                    //Users Fragment loads on start

                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.frameLayout, UsersFragment.class, null)
                            .commit();
                }
            }
        });

    }
}