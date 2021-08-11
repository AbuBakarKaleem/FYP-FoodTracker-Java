package com.app.foodtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.app.foodtracker.Utils.Utils;
import com.app.foodtracker.database.model.User;

public class SignUpActivity extends AppCompatActivity {

    private ImageView iv_registerUserBack;
    private EditText et_signUpFirstName;
    private EditText et_signUpLastName;
    private EditText et_signUpEmail;
    private EditText et_signUpPassword;
    private EditText et_signUpAddress;
    private EditText et_signUpPhoneNumber;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        iv_registerUserBack = findViewById(R.id.iv_registerUserBack);
        iv_registerUserBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        et_signUpFirstName = findViewById(R.id.et_signUpFirstName);
        et_signUpLastName = findViewById(R.id.et_signUpLastName);
        et_signUpEmail = findViewById(R.id.et_signUpEmail);
        et_signUpPassword = findViewById(R.id.et_signUpPassword);
        et_signUpAddress = findViewById(R.id.et_signUpAddress);
        et_signUpPhoneNumber = findViewById(R.id.et_signUpPhoneNumber);

        authViewModel = new ViewModelProvider(SignUpActivity.this).get(AuthViewModel.class);

    }

    public void signUpValidation(View view) {

        if (et_signUpFirstName.getText().toString().isEmpty()) {
            et_signUpFirstName.setError(getString(R.string.required));
            return;
        }
        if (et_signUpLastName.getText().toString().isEmpty()) {
            et_signUpLastName.setError(getString(R.string.required));
            return;
        }
        if (et_signUpEmail.getText().toString().isEmpty()) {
            et_signUpEmail.setError(getString(R.string.required));
            return;
        }
        if (!Utils.emailValidation(et_signUpEmail.getText().toString().trim())) {
            et_signUpEmail.setError(getString(R.string.invalidEmail));
            return;
        }
        if (et_signUpPassword.getText().toString().isEmpty()) {
            et_signUpPassword.setError(getString(R.string.required));
            return;
        }
        if (et_signUpPassword.getText().toString().length() <= 3) {
            et_signUpPassword.setError(getString(R.string.password_length_error));
            return;
        }
        if (et_signUpAddress.getText().toString().isEmpty()) {
            et_signUpAddress.setError(getString(R.string.required));
            return;
        }
        if (et_signUpPhoneNumber.getText().toString().isEmpty()) {
            et_signUpPhoneNumber.setError( getString(R.string.required));
            return;
        }
        /*if (et_signUpPhoneNumber.text.length<11) {
            et_signUpPhoneNumber.error = getString(R.string.invalidPhone)
            return
        }*/

        registerUser();

    }
    private void registerUser() {

        try {
            User user = new User(
                    et_signUpFirstName.getText().toString().trim(),
                    et_signUpLastName.getText().toString().trim(),
                    et_signUpEmail.getText().toString().trim(),
                    et_signUpPassword.getText().toString().trim(),
                    et_signUpAddress.getText().toString().trim(),
                    et_signUpPhoneNumber.getText().toString().trim()
            );
            Long insertedValue=authViewModel.registerUser(this, user);
            if(insertedValue>0){
                showToast(getString(R.string.user_register_successsfully));
                onBackPressed();
            }
            else if(insertedValue<0){
                showToast(getString(R.string.user_already_register));
            }else{
                showToast(getString(R.string.user_not_register));
            }

        } catch (Exception e) {
            Log.e("APP", e.getMessage().toString());
            showToast(getString(R.string.something_went_wrong));
        }

    }
    private void showToast(String message){
        Utils.showToast(this,message);
    }

}