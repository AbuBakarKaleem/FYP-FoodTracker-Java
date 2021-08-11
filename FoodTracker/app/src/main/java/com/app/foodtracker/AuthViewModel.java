package com.app.foodtracker;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.app.foodtracker.database.model.User;
import com.app.foodtracker.repository.Repository;


public class AuthViewModel extends ViewModel {
    public Long registerUser(Context context, User user){
        return Repository.registerUser(context, user);
    }

    public User loginUser(Context context,String email,String password){
        return Repository.loginUser(context,email,password);
    }
}
