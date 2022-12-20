package com.example.myride;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myride.models.garageModel;
import com.example.myride.recyclerView.viewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RecyclerViewAdapter extends RecyclerView.Adapter<viewHolder> {
    ArrayList<garageModel> mGarage = new ArrayList<garageModel>();
    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
    Context mContext;

       public RecyclerViewAdapter(ArrayList<garageModel> mGarage, Context mContext) {
        this.mGarage = mGarage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.recycler_item, parent,false);

            viewHolder viewHolder = new viewHolder(itemLayoutView);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mGarage.get(position).getPicture())
                .into(holder.image2);
        holder.nicknameText.setText(mGarage.get(position).getNickname());
        holder.ymmText.setText((mGarage.get(position).getYear()) + " " + (mGarage.get(position).getMake()) + " " + mGarage.get(position).getModel());
        holder.crText.setText((mGarage.get(position).getColor()) + " " + (mGarage.get(position).getRegistration()));


        // get the car nickname by positions
        String nickname = mGarage.get(position).getNickname();




        holder.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ServiceHistory.class);
                i.putExtra("nickname", nickname);
                mContext.startActivity(i);
            }

        });

        holder.serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ServiceHistory.class);
                i.putExtra("nickname", nickname);
                mContext.startActivity(i);
            }

        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Edit Clicked."+nickname, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext, AddCar.class);
                i.putExtra("nickname", nickname);
                mContext.startActivity(i);
            }

        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), nickname+" Deleted.", Toast.LENGTH_SHORT).show();
                reff.child("myGarage").child(nickname).removeValue();
                Intent i = new Intent(mContext, MyGarage.class);
                mContext.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mGarage.size();
    }
}
