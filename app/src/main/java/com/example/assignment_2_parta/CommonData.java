package com.example.assignment_2_parta;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommonData extends ViewModel
{
    public MutableLiveData<User> selectedUser;
    public MutableLiveData<UserList> userList;

    public CommonData()
    {
        selectedUser = new MutableLiveData<User>();
        selectedUser.setValue(null);

        userList = new MutableLiveData<UserList>();
        userList.setValue(null);
    }

    public void setSelectedUser(User value)
    {
        this.selectedUser.setValue(value);
    }
    public User getSelectedUser()
    {
        return this.selectedUser.getValue();
    }

    public void setUserList(UserList value)
    {
        this.userList.setValue(value);
    }
    public UserList getUserList()
    {
        return this.userList.getValue();
    }
}
