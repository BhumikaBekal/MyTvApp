package com.example.mytv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RCadapter extends FirebaseRecyclerAdapter<RCmodel,RCadapter.myviewholder> {


    public RCadapter(@NonNull  FirebaseRecyclerOptions<RCmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  RCadapter.myviewholder holder, int position, @NonNull  RCmodel model) {
        holder.channels.setText(model.getCname());
        holder.theprice.setText(model.getPrice());
        Glide.with(holder.img.getContext())
                .load(model.getLogo())
                .timeout(3000)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView channels,theprice;
        FirebaseAuth mAuth;
        Button channeladd;


        public myviewholder(@NonNull  View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.channellogo);
            channels=(TextView)itemView.findViewById(R.id.channelname);
            theprice=(TextView)itemView.findViewById(R.id.channelprice);
            channeladd=(Button)itemView.findViewById(R.id.addchannel);
            channeladd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("tempcartchannel",channels.getText().toString());
                    map.put("tempcartprice",theprice.getText().toString());
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseDatabase.getInstance().getReference().child("tempcart").child(mAuth.getCurrentUser().getUid()).push()
                            .setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    Toast.makeText(v.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            Toast.makeText(v.getContext(), "e", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }
    }
}
