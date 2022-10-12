package com.example.assignment_2_parta;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommonData extends ViewModel
{
    public MutableLiveData<User> selectedUser;
    public MutableLiveData<UserList> userList;
    public MutableLiveData<PostList> postList;

    public CommonData()
    {
        this.selectedUser = new MutableLiveData<User>();
        this.selectedUser.setValue(null);

        this.userList = new MutableLiveData<UserList>();
        this.userList.setValue(new UserList());

        this.postList = new MutableLiveData<PostList>();
        this.postList.setValue(new PostList());
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

    public void setPostList(PostList value)
    {
        this.postList.setValue(value);
    }
    public PostList getPostList()
    {
        return this.postList.getValue();
    }
}
