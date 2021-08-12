package com.app.foodtracker.database.model;

import static com.app.foodtracker.Utils.Utils.USER_COL_ADDRESS;
import static com.app.foodtracker.Utils.Utils.USER_COL_EMAIL;
import static com.app.foodtracker.Utils.Utils.USER_COL_FIRST_NAME;
import static com.app.foodtracker.Utils.Utils.USER_COL_LAST_NAME;
import static com.app.foodtracker.Utils.Utils.USER_COL_PASSWORD;
import static com.app.foodtracker.Utils.Utils.USER_COL_PHONE_NUMBER;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users")
public class User {

    @ColumnInfo(name = USER_COL_FIRST_NAME) String firstName;
    @ColumnInfo(name = USER_COL_LAST_NAME) String lastName;
    @PrimaryKey@ColumnInfo(name = USER_COL_EMAIL) @NonNull String email;
    @ColumnInfo(name = USER_COL_PASSWORD) String password;
    @ColumnInfo(name = USER_COL_ADDRESS) String address;
    @ColumnInfo(name = USER_COL_PHONE_NUMBER) String phoneNumber;

    public User(String firstName, String lastName, @NonNull String email, String password, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
