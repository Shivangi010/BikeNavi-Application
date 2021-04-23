package com.example.bikenavi.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.bikenavi.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {

    // constants
    private static final String TAG = "ProfileActivity";

    // widgets
    private EditText pFirstName, pLastName,
        pEmail, pPhone;

    // variables
    private FirebaseAuth pfAuth;
    private FirebaseFirestore fireStore;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findAllViews();
        pEmail.setEnabled(false);

        DocumentReference documentReference = fireStore.collection("Users").
                document(userId);

        documentReference.addSnapshotListener(this, (value, error) -> {
            pFirstName.setText(value.getString("firstName"));
            pLastName.setText(value.getString("lastName"));
            pEmail.setText(value.getString("email"));
            pPhone.setText(value.getString("phoneNumber"));
        });
    }

    private void findAllViews() {
        pFirstName = findViewById(R.id.etFirstName);
        pLastName = findViewById(R.id.etLastName);
        pEmail = findViewById(R.id.etEmail);
        pPhone = findViewById(R.id.etPhoneNumber);

        pfAuth = FirebaseAuth.getInstance();
        fireStore =  FirebaseFirestore.getInstance();
        userId = pfAuth.getCurrentUser().getUid();
    }
}