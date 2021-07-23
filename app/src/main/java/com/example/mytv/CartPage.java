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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public class CartPage extends AppCompatActivity {

    RecyclerView recview;
    myadapter adapter;
    TextView totalbill;
    private DatabaseReference mDatabase,cdatabase;
    private String TAG =" main";
    private Button startpayment;
    FirebaseAuth mAuth;
    String cemail;
    String cphone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        FirebaseApp.initializeApp(this);
        totalbill=findViewById(R.id.orderamount);
        startpayment = findViewById(R.id.startpayment);

        recview=(RecyclerView)findViewById(R.id.rccart);
        recview.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();

        FirebaseRecyclerOptions<cartmodel> options =
                new FirebaseRecyclerOptions.Builder<cartmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("tempcart").child(mAuth.getCurrentUser().getUid()), cartmodel.class)
                        .build();
        mDatabase=FirebaseDatabase.getInstance().getReference().child("tempcart").child(mAuth.getCurrentUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                int sum=0;
                for(DataSnapshot ds: snapshot.getChildren())
                {

                        Map<String, Object> m = (Map<String, Object>) ds.getValue();
                        Object withcurrency = m.get("tempcartprice");
                        String removeCurrency = withcurrency.toString().substring(3);
                        int pvalue = Integer.parseInt(String.valueOf(removeCurrency));
                        sum += pvalue;

                }
                totalbill.setText(String.valueOf(sum));

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });

        adapter=new myadapter(options);
        recview.setAdapter(adapter);

        startpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalbill.getText().toString().equals("0"))
                {
                    Toast.makeText(CartPage.this, "Amount is empty", Toast.LENGTH_LONG).show();
                }else {
                    startPayment();
                }
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void startPayment() {

        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();



        try {
            cdatabase=FirebaseDatabase.getInstance().getReference().child("UserData").child(mAuth.getCurrentUser().getUid());
            cdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cemail=snapshot.child("Email").getValue().toString();
//                    Log.e("email thing", snapshot.child("Email").getValue().toString() );
                    cphone=snapshot.child("Phone").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            JSONObject options = new JSONObject();

            options.put("name", "My Tv");
            options.put("description", "App Payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");


            String payment = totalbill.getText().toString();
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
            JSONObject preFill = new JSONObject();
            preFill.put("email",cemail);
            preFill.put("contact",cphone);
            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onPaymentSuccess(String s) {
        mDatabase=FirebaseDatabase.getInstance().getReference().child("tempcart").child(mAuth.getCurrentUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Map<String, Object> m = (Map<String, Object>) ds.getValue();
                    Object chan=m.get("tempcartchannel");
                    Object pricee = m.get("tempcartprice");
                    Map<String, Object> finalmap = new HashMap<>();
                    finalmap.put("finalcartchannel",chan);
                    finalmap.put("finalcartprice",pricee);
                    FirebaseDatabase.getInstance().getReference().child("donecart").child(mAuth.getCurrentUser().getUid()).push()
                            .setValue(finalmap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    exit(0);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {

                        }
                    });



                }


            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
        Toast.makeText(CartPage.this, "Payment successfully done! " , Toast.LENGTH_LONG).show();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().getReference().child("tempcart")
                .child(mAuth.getCurrentUser().getUid())
                .removeValue();
        totalbill.setText("0.0");


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