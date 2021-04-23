package com.example.bikenavi.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikenavi.R;
import com.example.bikenavi.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "LoginActivity";

    // widgets
    private EditText lEmail, lPassword;

    //variables
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findAllViews();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void findAllViews() {
        lEmail = findViewById(R.id.etEmail);
        lPassword = findViewById(R.id.etPassword);
        fAuth = FirebaseAuth.getInstance();
    }

    public void onSignUpTextClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        finish();
    }

    public void onForgotPasswordTextClick(View view) {
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }

    public void userValidator(String email, String password){

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            lEmail.setError(getResources().getString(R.string.err_email));
            lPassword.setError(getResources().getString(R.string.err_password));
        }
    }

    public void onUserLoginClick(View view){
        String userEmail = lEmail.getText().toString().trim();
        String userPassword = lPassword.getText().toString().trim();

        userValidator(userEmail, userPassword);

        // login the user
        fAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,
                            R.string.login_success, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,
                            getResources().getString(R.string.login_error) + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}