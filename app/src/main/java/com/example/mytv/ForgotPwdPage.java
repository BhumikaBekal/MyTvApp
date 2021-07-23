package com.example.mytv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPwdPage extends AppCompatActivity {
    private EditText emailforgotpwd;
    private Button sendmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd_page);
        sendmail=findViewById(R.id.sendmail);
        emailforgotpwd=findViewById(R.id.emailforgotpwd);
        firebaseAuth = FirebaseAuth.getInstance();
        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(emailforgotpwd.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull  Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPwdPage.this, "Password reset link sent to your Email", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ForgotPwdPage.this, LoginPage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ForgotPwdPage.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}