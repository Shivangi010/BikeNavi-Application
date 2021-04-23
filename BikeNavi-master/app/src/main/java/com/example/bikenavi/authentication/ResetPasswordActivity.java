package com.example.bikenavi.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikenavi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "ResetPasswordActivity";

    // widgets
    private EditText rpEmail;

    // variables
    private FirebaseAuth rpfAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    public void onSendClick(View view) {
        rpfAuth = FirebaseAuth.getInstance();

        rpEmail = findViewById(R.id.resetEmail);
        String email = rpEmail.getText().toString().trim();

        rpfAuth.sendPasswordResetEmail(email).addOnSuccessListener(aVoid -> {
            Toast.makeText(ResetPasswordActivity.this,
                    R.string.emailPwd_success, Toast.LENGTH_SHORT).show();

            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
        }).addOnFailureListener(e -> Toast.makeText(ResetPasswordActivity.this,
                getResources().getString(R.string.emailPwd_error) + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}