package com.example.mytv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {
  TextView callnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        callnumber=findViewById(R.id.callnumber);
        callnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_DIAL);
                String phoneno=callnumber.getText().toString();
                String p="tel:"+phoneno;
                intent1.setData(Uri.parse(p));
                startActivity(intent1);
            }
        });
    }
}