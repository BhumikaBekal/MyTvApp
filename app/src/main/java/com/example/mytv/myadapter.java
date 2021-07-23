package com.example.mytv;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myadapter extends FirebaseRecyclerAdapter<cartmodel,myadapter.myviewholder>
{
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public myadapter(@NonNull FirebaseRecyclerOptions<cartmodel> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final cartmodel cartmodel)
    {
        holder.cname.setText(cartmodel.getTempcartchannel());
        holder.price.setText(cartmodel.getTempcartprice());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.cname.getContext());
                builder.setTitle("Delete this item");
                builder.setMessage("Are your sure you want to delete this item?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseReference pos=getRef(position);
                        FirebaseDatabase.getInstance().getReference().child("tempcart")
                                .child(mAuth.getCurrentUser().getUid())
                                .child(pos.getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);
        return new myviewholder(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        ImageView delete;
        TextView cname,price;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            cname=(TextView)itemView.findViewById(R.id.cartchannelname);
            price=(TextView)itemView.findViewById(R.id.cartchannelprice);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);

        }
    }
}
