package com.example.midtermproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText usernameEditText, passwordEditText, emailEditText, confirmPasswordEditText;
    private Button signupButton;
    private TextView loginTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = findViewById(R.id.tietUsername);
        passwordEditText = findViewById(R.id.tiePassword);
        emailEditText = findViewById(R.id.tietEmail);
        confirmPasswordEditText = findViewById(R.id.tieConfirmPassword);
        signupButton = findViewById(R.id.btnSignUp);
        loginTextView = findViewById(R.id.tvLogin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();

                if (validate(username, password, email, confirmPasswordEditText.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String uid = user.getUid();
                                        User newUser = new User(username, email,null);
                                        mDatabase.child("users").child(uid).setValue(newUser);
                                        Toast.makeText(SignupActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(customerIntent);
            }
        });
    }
    private boolean validate(String username, String password, String email, String confirmPassword) {
        // set error for each invalid input
        boolean valid = true;
        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.setError("Please fill in all fields");
            valid = false;
        }
        if (password.isEmpty() || password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            valid = false;
        }
        else if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Password does not match");
            valid = false;
        }
        if (email.isEmpty()) {
            emailEditText.setError("Please fill in all fields");
            valid = false;
        }
        if (username.isEmpty()) {
            usernameEditText.setError("Please fill in all fields");
            valid = false;
        }
        return valid;



    }



}
