package com.app.foodtracker.Utils;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Utils {

    public static String DB_NAME="FoodTracker";

       public static final String USER_COL_FIRST_NAME = "first_name";
       public static final String USER_COL_LAST_NAME = "last_name";
       public static final String USER_COL_EMAIL = "email";
       public static final String USER_COL_PASSWORD = "password";
       public static final String USER_COL_PHONE_NUMBER = "phone_number";
       public static final String USER_COL_ADDRESS = "address";
       
       public static  final String MEAL_COL_TYPE = "type";
       public static  final String MEAL_COL_DATE = "date";
       public static  final String MEAL_COL_TIME = "time";
       public static  final String MEAL_COL_DESCRIPTION = "description";

       public static String STR_BREAKFAST="Breakfast";
       public static String STR_LUNCH="Lunch";
       public static String STR_DINNER="Dinner";
       public static String STR_SNACKS="Snacks";

    Pattern EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
            );
    public static int BREAKFAST = 0;
    public static int LUNCH = 1;
    public static int DINNER = 2;
    public static int SNACKS = 3;

    public static Boolean emailValidation(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
