package com.app.foodtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;

import androidx.appcompat.app.AppCompatActivity;

import com.app.foodtracker.Utils.Utils;
import com.app.foodtracker.database.model.User;
import com.app.foodtracker.session.SessionManager;
import com.app.foodtracker.ui.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_registerUser;
    private EditText et_loginEmail;
    private EditText et_loginPassword;
    private AuthViewModel authViewModel;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init() {

        session = new SessionManager(LoginActivity.this);
        session.checkLogin();

        tv_registerUser = findViewById(R.id.tv_registerUser);
        et_loginEmail = findViewById(R.id.et_loginEmail);
        et_loginPassword = findViewById(R.id.et_loginPassword);

        tv_registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        authViewModel = new ViewModelProvider(LoginActivity.this).get(AuthViewModel.class);
    }
    public void validation(View view) {

        if (et_loginEmail.getText().toString().isEmpty()) {
            et_loginEmail.setError(getString(R.string.required));
            return;
        }
        if (!Utils.emailValidation(et_loginEmail.getText().toString().trim())) {
            et_loginEmail.setError( getString(R.string.invalidEmail));
            return;
        }
        if (et_loginPassword.getText().toString().isEmpty()) {
            et_loginPassword.setError( getString(R.string.required));
            return;
        }
        login();
    }
    private void login() {
        try {
            User userInfo = authViewModel.loginUser(
                    LoginActivity.this,
            et_loginEmail.getText().toString().trim(),
                    et_loginPassword.getText().toString().trim()
            );
            if (userInfo==null) {
                showToast(getString(R.string.invalidEmailOrPassword));
            } else {
                onLoginSuccess(userInfo);
            }
        } catch ( Exception e) {
            showToast(e.getMessage().toString());
        }
    }

    private void showToast(String message) {
        Utils.showToast(LoginActivity.this, message);
    }

    private void onLoginSuccess(User userInfo) {
        session.createLoginSession(userInfo);
        showToast("Login Successfully");
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finishAffinity();
    }
}