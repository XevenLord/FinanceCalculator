package com.example.financecalculator.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.financecalculator.model.UserDto;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<UserDto> user = new MutableLiveData<>();

    public void setUser(UserDto user) {
        this.user.setValue(user);
    }

    public LiveData<UserDto> getUser() {
        return user;
    }
}
