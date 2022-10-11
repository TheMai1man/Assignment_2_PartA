package com.example.assignment_2_parta;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchResponseViewModel extends ViewModel
{
    public MutableLiveData<String> response;

    public SearchResponseViewModel()
    {
        response = new MutableLiveData<String>();
    }

    public String getResponse()
    {
        return response.getValue();
    }
    public void setResponse(String value)
    {
        response.setValue(value);
    }
}
