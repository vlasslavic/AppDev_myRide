package com.example.myride.recyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myride.R;

public class viewHolderService extends RecyclerView.ViewHolder {

    public ImageView image2;
    public ImageView image3;
    public TextView serviceDetails , shopName, serviceStatus;
    public TextView txtTask;
    public TextView txtService;
    public ImageView editBtn;
    public ImageView deleteBtn;
    public LinearLayout parentLayout,serviceBtn;
    LinearLayout taskLayout;

    public viewHolderService(@NonNull View itemView) {
        super(itemView);

        serviceDetails = itemView.findViewById(R.id.nickname);
        shopName = itemView.findViewById(R.id.serviceDetails);
        serviceStatus = itemView.findViewById(R.id.serviceStatus);
        txtTask = itemView.findViewById(R.id.txttobecompleted);
        txtService = itemView.findViewById(R.id.txtwhereservice);
        image2 = itemView.findViewById(R.id.imageView2);
        image3 = itemView.findViewById(R.id.imageCompany);
        editBtn = itemView.findViewById(R.id.editBtn);
        deleteBtn = itemView.findViewById(R.id.deleteBtn);
        parentLayout = itemView.findViewById(R.id.parentid);
        taskLayout = itemView.findViewById(R.id.mainid);
        serviceBtn = itemView.findViewById(R.id.serviceBtn);

    }
}
