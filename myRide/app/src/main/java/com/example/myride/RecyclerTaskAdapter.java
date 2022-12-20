package com.example.myride;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myride.models.garageModel;
import com.example.myride.models.taskModel;
import com.example.myride.recyclerView.viewHolder;

import java.util.ArrayList;

public class RecyclerTaskAdapter extends RecyclerView.Adapter<viewHolder> {
    ArrayList<taskModel> mtasks = new ArrayList<>();
    Context mContext;

    public RecyclerTaskAdapter(ArrayList<taskModel> mtasks, Context mContext) {
        this.mtasks = mtasks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.task_cards, parent,false);

        viewHolder viewHolder = new viewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Glide.with(mContext)
                        .asBitmap().load(mtasks.get(position).getCompany()).into(holder.image3);
        holder.txtTask.setText(mtasks.get(position).getTask());
        holder.txtService.setText((mtasks.get(position).getServiceLocation()));



    }

    @Override
    public int getItemCount() {
        return mtasks.size();
    }

    public interface OnItemClickListener {
        void onItemClick(taskModel model);
    }
}
