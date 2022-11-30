package com.example.myride;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myride.models.garageModel;
import com.example.myride.recyclerView.viewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<viewHolder> {

     ArrayList<garageModel> mGarage = new ArrayList<>();

    Context mContext;

       public RecyclerViewAdapter(ArrayList<garageModel> mGarage, Context mContext) {
        this.mGarage = mGarage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//           View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,
//                   parent, false);
////           return  new viewHolder(view);
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.recycler_item, parent,false);

            viewHolder viewHolder = new viewHolder(itemLayoutView);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mGarage.get(position).getPicture())
                .into(holder.image2);
        holder.nicknameText.setText(mGarage.get(position).getNickname());
        holder.ymmText.setText((mGarage.get(position).getYear())+" "+(mGarage.get(position).getMake())+" "+mGarage.get(position).getModel());
        holder.crText.setText((mGarage.get(position).getColor())+" "+(mGarage.get(position).getRegistration()));

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Here You Do Your Click Magic
//            });


//        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//                Intent i = new Intent(this, AddCar.class);
//                startActivity(i);
//            }
//
//        });

    }

    public interface OnItemClickListener {
        void onItemClick(garageModel model);
    }

    @Override
    public int getItemCount() {
        return mGarage.size();
    }
}
