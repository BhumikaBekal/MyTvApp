package com.example.mytv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {
    private TextView login_text;
    EditText Name_et, Phone_et, Email_et, Address_et, pwd_et;
    Button signup_button;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    String fullname, phone, email, address, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        signup_button = findViewById(R.id.signup_button);
        login_text = findViewById(R.id.login_text);
        Name_et = findViewById(R.id.Name_et);
        Phone_et = findViewById(R.id.Phone_et);
        Email_et = findViewById(R.id.Email_et);
        Address_et = findViewById(R.id.Address_et);
        pwd_et = findViewById(R.id.pwd_et);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserData");

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupPage.this, LoginPage.class);
                startActivity(i);
            }
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullname() | !validatePhone() | !validateEmail() | !validateAddress() | !validatePassword()) {
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserData data = new UserData(fullname, phone, email, address);
                            FirebaseDatabase.getInstance().getReference("UserData")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignupPage.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SignupPage.this, HomePage.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                        } else {
                            Toast.makeText(SignupPage.this, "Check email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private boolean validateEmail() {
        email = Email_et.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignupPage.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignupPage.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean validateFullname() {
        fullname = Name_et.getText().toString().trim();
        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(SignupPage.this, "Enter Your Full Name", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePhone() {
        phone = Phone_et.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(SignupPage.this, "Enter Your Phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }

    private boolean validateAddress() {
        address = Address_et.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(SignupPage.this, "Enter Your Phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }

    private boolean validatePassword() {
        password = pwd_et.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignupPage.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
            return false;
        }  else if (password.length() <= 6) {
            Toast.makeText(SignupPage.this, "Password is Very Short", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}


