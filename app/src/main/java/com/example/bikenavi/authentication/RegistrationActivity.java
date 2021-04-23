package com.example.bikenavi.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikenavi.R;
import com.example.bikenavi.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    // constants
    private static final String TAG = "RegistrationActivity";

    // widgets
    private EditText rFirstName, rLastName,
            rEmail, rPhone, rPassword;

    // variables
    private String userId;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findAllViews();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void findAllViews() {
        rFirstName = findViewById(R.id.etFirstName);
        rLastName = findViewById(R.id.etLastName);
        rEmail = findViewById(R.id.etEmail);
        rPassword = findViewById(R.id.etPassword);
        rPhone = findViewById(R.id.etPhoneNumber);

        fAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
    }

    public void onLoginTextClick(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }

    public void userValidator(String firstName, String lastName,
                                String email, String password,
                                String phoneNumber){

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(phoneNumber)){
            rFirstName.setError(getResources().getString(R.string.err_firstName));
            rLastName.setError(getResources().getString(R.string.err_lastName));
            rEmail.setError(getResources().getString(R.string.err_email));
            rPassword.setError(getResources().getString(R.string.err_password));
            rPhone.setError(getResources().getString(R.string.err_phone));
        }

        if (password.length() < 6){
            rPassword.setError(getResources().getString(R.string.valid_password));
        }
    }

    public void onSignUpClick(View view){
        String firstName = rFirstName.getText().toString().trim();
        String lastName = rLastName.getText().toString().trim();
        String email = rEmail.getText().toString().trim();
        String password = rPassword.getText().toString().trim();
        String phoneNumber = rPhone.getText().toString().trim();

        userValidator(firstName, lastName, email, password, phoneNumber);

        // register the user in Firebase

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this,
                            R.string.user_success, Toast.LENGTH_LONG).show();

                    userId = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fireStore.collection("Users")
                            .document(userId);

                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("firstName", firstName);
                    userMap.put("lastName", lastName);
                    userMap.put("email", email);
                    userMap.put("phoneNumber", phoneNumber);

                    documentReference.set(userMap).
                            addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: user profile is created" + userId)) .
                            addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.toString()));

                    startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
                    finish();

                } else {
                    Toast.makeText(RegistrationActivity.this,
                            getResources().getString(R.string.user_error) + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}