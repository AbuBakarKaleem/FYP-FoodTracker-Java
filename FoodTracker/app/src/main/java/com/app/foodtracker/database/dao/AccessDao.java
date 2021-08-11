package com.app.foodtracker.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.foodtracker.database.model.MealRecord;
import com.app.foodtracker.database.model.User;

import java.util.List;

@Dao
public interface AccessDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public Long registerUser(User users);

    /*@Query("SELECT * FROM Users WHERE email=:email")
    public LiveData<User> isEmailExist(String email);*/

    @Query("SELECT * FROM Users WHERE email=:email AND password=:password")
    public User loginUser(String email,String password);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public Long insertMealRecord(MealRecord record);

    @Query("SELECT * FROM MealRecord")
    public List<MealRecord> getAllMealRecords();

}
