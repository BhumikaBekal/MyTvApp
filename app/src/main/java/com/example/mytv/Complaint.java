package com.example.mytv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Complaint extends AppCompatActivity {

    EditText txtname,txtemail,txtphone,txtcomplaint;
    Button submitComplaint;
    DatabaseReference reff;
    complaintModel complaintmodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        txtname=findViewById(R.id.comName);
        txtemail=findViewById(R.id.comMail);
        txtphone=findViewById(R.id.comPhone);
        txtcomplaint=findViewById(R.id.editTextTextMultiLine2);
        submitComplaint=findViewById(R.id.submitComplaint);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        reff= FirebaseDatabase.getInstance().getReference().child("complaints").child(mAuth.getCurrentUser().getUid());

        complaintmodel=new complaintModel();
        submitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long phone=Long.parseLong(txtphone.getText().toString().trim());
                complaintmodel.setName(txtname.getText().toString().trim());
                complaintmodel.setEmail(txtemail.getText().toString().trim());
                complaintmodel.setPhone(phone);
                complaintmodel.setComplaint(txtcomplaint.getText().toString().trim());
                reff.push().setValue(complaintmodel);
                Toast.makeText(Complaint.this,"Your complaint is submitted",Toast.LENGTH_LONG).show();
                Intent i5=new Intent(Complaint.this,HomePage.class);
                startActivity(i5);


            }
        });






    }
}