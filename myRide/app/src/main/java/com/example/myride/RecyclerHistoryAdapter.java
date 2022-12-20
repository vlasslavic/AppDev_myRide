package com.example.myride;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myride.models.garageModel;
import com.example.myride.models.serviceHistoryModel;
import com.example.myride.recyclerView.viewHolderService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerHistoryAdapter extends RecyclerView.Adapter<viewHolderService> {
    private final String nickname;
    ArrayList<serviceHistoryModel> mHistory = new ArrayList<serviceHistoryModel>();
    private final FirebaseAuth mAuth= FirebaseAuth.getInstance();
    DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
    Context mContext;

       public RecyclerHistoryAdapter(ArrayList<serviceHistoryModel> mHistory, Context mContext, String nickname) {
        this.mHistory = mHistory;
        this.mContext = mContext;
        this.nickname = nickname;
    }

    @NonNull
    @Override
    public viewHolderService onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.recycler_service, parent,false);

            viewHolderService viewHolderService = new viewHolderService(itemLayoutView);
            return viewHolderService;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderService holder, @SuppressLint("RecyclerView") int position) {

//        Glide.with(mContext)
//                .asBitmap()
//                .load(mHistory.get(position).get())
//                .into(holder.image2);
        holder.serviceDetails.setText(mHistory.get(position).getServiceDetails());
        holder.shopName.setText((mHistory.get(position).getShopName()));
        holder.serviceStatus.setText((mHistory.get(position).getServiceStatus())+"  @ "+mHistory.get(position).getOdometer()+" KM");


        // get the service ID by positions
        String serviceID = mHistory.get(position).getServiceID();


        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Edit Clicked."+serviceID, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext, AddTask.class);
                i.putExtra("nickname", nickname);
                i.putExtra("serviceID", serviceID);
                mContext.startActivity(i);
            }

        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), serviceID+" Deleted.", Toast.LENGTH_SHORT).show();
                reff.child("myGarage").child(nickname).child("serviceHistory").child(serviceID).removeValue();
                Intent i = new Intent(mContext, ServiceHistory.class);
                i.putExtra("nickname", nickname);
                mContext.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mHistory.size();
    }
}
