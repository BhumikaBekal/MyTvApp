package com.example.mytv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Profile extends AppCompatActivity {
    RecyclerView precview;
    FirebaseAuth mAuth;
    Profileadapter padapter;
    private DatabaseReference pDatabase,puserDatabase;
    TextView ptotalbill,pname,pphone,pemail,Profileamount;
    String pusername,puseremail,puserphone;
    Button recharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseApp.initializeApp(this);
        ptotalbill=findViewById(R.id.Profileamount);
        precview=(RecyclerView)findViewById(R.id.yourpacks);
        pname=findViewById(R.id.pname);
        pphone=findViewById(R.id.pphone);
        pemail=findViewById(R.id.pemail);
        recharge=findViewById(R.id.recharge);
        Profileamount=findViewById(R.id.Profileamount);



        mAuth = FirebaseAuth.getInstance();
        puserDatabase=FirebaseDatabase.getInstance().getReference().child("UserData").child(mAuth.getCurrentUser().getUid());
        puserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                pusername = snapshot2.child("Fullname").getValue().toString();
                puseremail=snapshot2.child("Email").getValue().toString();
                puserphone=snapshot2.child("Phone").getValue().toString();
                pname.setText(pusername);
                pphone.setText(puserphone);
                pemail.setText(puseremail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        precview.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Profilecartmodel> options2 =
                new FirebaseRecyclerOptions.Builder<Profilecartmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("donecart").child(mAuth.getCurrentUser().getUid()),Profilecartmodel.class)
                        .build();

        padapter = new Profileadapter(options2);
        precview.setAdapter(padapter);

        pDatabase=FirebaseDatabase.getInstance().getReference().child("donecart").child(mAuth.getCurrentUser().getUid());
        pDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int psum=0;
                for(DataSnapshot ds: snapshot.getChildren()){
                    Map<String,Object> m=(Map<String, Object>) ds.getValue();
                    Object withcurrency=m.get("finalcartprice");
                    String removeCurrency=withcurrency.toString().substring(3);
                    int ppvalue=Integer.parseInt(String.valueOf(removeCurrency));
                    psum+=ppvalue;
                }
                ptotalbill.setText(String.valueOf(psum));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Profileamount.getText().toString().equals("0.0"))
                {
                    Toast.makeText(Profile.this, "Amount is empty", Toast.LENGTH_LONG).show();
                }else {
                    startPayment();
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        padapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        padapter.stopListening();
    }


    public void startPayment() {

        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();



        try {



            JSONObject options = new JSONObject();

            options.put("name", "My Tv");
            options.put("description", "App Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");


            String payment = ptotalbill.getText().toString();
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email",puseremail);
            preFill.put("contact",puserphone);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    public void onPaymentSuccess(String s) {


        Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();


    }

    public void onPaymentError(int i, String s) {
        Log.e(TAG,  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }
}