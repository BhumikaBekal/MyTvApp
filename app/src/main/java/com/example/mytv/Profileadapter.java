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
import com.google.firebase.database.FirebaseDatabase;

public class Profileadapter extends FirebaseRecyclerAdapter<Profilecartmodel,Profileadapter.myviewholder>
{
    public Profileadapter(@NonNull FirebaseRecyclerOptions<Profilecartmodel> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Profilecartmodel profilecartmodel)
    {
        holder.pcname.setText(profilecartmodel.getFinalcartchannel());
        holder.pprice.setText(profilecartmodel.getFinalcartprice());


        holder.pdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.pcname.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete this item?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseDatabase.getInstance().getReference().child("donecart")
                                .child(mAuth.getCurrentUser().getUid())
                                .child(getRef(position).getKey()).removeValue();
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

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.profileitem,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {

        ImageView pdelete;
        TextView pcname,pprice;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            pcname=(TextView)itemView.findViewById(R.id.pcname);
            pprice=(TextView)itemView.findViewById(R.id.pprice);
            pdelete=(ImageView)itemView.findViewById(R.id.pdelete);
        }
    }
}

