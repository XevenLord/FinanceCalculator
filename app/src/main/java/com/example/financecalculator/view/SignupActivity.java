package com.example.financecalculator.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financecalculator.R;
import com.example.financecalculator.model.UserDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private EditText etEmail;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etBirthYear;
    private Button btnSignup;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        etEmail = findViewById(R.id.et_email);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etBirthYear = findViewById(R.id.et_birth_year);
        btnSignup = findViewById(R.id.btn_signup);
        btnLogin = findViewById(R.id.btn_login);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Handle Sign Up button click
        btnSignup.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String birthYear = etBirthYear.getText().toString().trim();

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || birthYear.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                signUpUser(email, username, password, birthYear);
            }
        });

        // Handle Login button click
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void signUpUser(String email, String username, String password, String birthYear) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                            @Override
                            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    UserDto userDto = new UserDto(username, email, Integer.parseInt(birthYear));

                                    // Add a new document with a generated ID
                                    mFirestore.collection("users").document(user.getUid())
                                            .set(userDto)
                                            .addOnSuccessListener(aVoid -> {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                                Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.w(TAG, "Error writing document", e);
                                                Toast.makeText(SignupActivity.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                            });

                                    // Remove the listener once the user object is obtained
                                    mAuth.removeAuthStateListener(this);
                                } else {
                                    Toast.makeText(SignupActivity.this, "Failed to retrieve user", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Failed to retrieve user");
                                }
                            }
                        };

                        // Add the listener to FirebaseAuth instance
                        mAuth.addAuthStateListener(authStateListener);
                    } else {
                        Toast.makeText(SignupActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Registration failed", task.getException());
                    }
                });
    }

}
