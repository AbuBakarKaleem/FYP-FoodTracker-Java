package com.app.foodtracker.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.app.foodtracker.LoginActivity;
import com.app.foodtracker.database.model.User;
import com.app.foodtracker.ui.HomeActivity;
import com.google.gson.Gson;

public class SessionManager {


    public SessionManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreferences pref ;

    // Editor for Shared preferences
    public SharedPreferences.Editor editor;

    // Context
    public Context _context;

    private int PRIVATE_MODE = 0;
    private String KEY_USER = "user_info";
    private String KEY_LAST_BREAKFAST="last_breakfast";
    private String KEY_LAST_LUNCH="last_lunch";
    private String KEY_LAST_DINNER="last_dinner";
    private String KEY_LAST_SNACKES="last_snacks";

    private String PREF_NAME = "FoodTracker Preference";
    private String IS_LOGIN = "IsLoggedIn";

    public void createLoginSession(User userInfo) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER, objectToString(userInfo));
        editor.putString(KEY_LAST_BREAKFAST, "");
        editor.putString(KEY_LAST_LUNCH, "");
        editor.putString(KEY_LAST_DINNER, "");
        editor.putString(KEY_LAST_SNACKES, "");
        editor.commit();
    }

    public User getUserDetails(){
        return stringToObject(pref.getString(KEY_USER, null)) ;
    }
    public String getLastBreakFast(){
        return pref.getString(KEY_LAST_BREAKFAST, null);
    }
    public String getLastLunch(){
        return pref.getString(KEY_LAST_LUNCH, null);
    }
    public String getLastDinner(){
        return pref.getString(KEY_LAST_DINNER, null);
    }

    public void setLastBreakfast(String value){
        editor.putString(KEY_LAST_BREAKFAST,value);
        editor.commit();
    }
    public void setLastLunch(String value){
        editor.putString(KEY_LAST_LUNCH,value);
        editor.commit();
    }
    public void setLastDinner(String value){
        editor.putString(KEY_LAST_DINNER,value);
        editor.commit();
    }
    public String getLastSnacks(){
        return pref.getString(KEY_LAST_SNACKES, null);
    }

    public void checkLogin(){
        if (this.isLoggedIn()) {

            Intent i = new Intent(_context, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    private String objectToString(User userInfo){
        Gson gson = new Gson();
        return gson.toJson(userInfo);
    }
    private User stringToObject(String value){
        Gson gson = new Gson();
        return gson.fromJson(value, User.class);
    }
    private Boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }




}
