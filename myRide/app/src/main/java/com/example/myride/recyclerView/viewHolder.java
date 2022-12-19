package com.example.myride.recyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myride.R;

public class viewHolder extends RecyclerView.ViewHolder {

    public ImageView image2;
    public ImageView image3;
    public TextView nicknameText;
    public TextView ymmText;
    public TextView crText;
    public TextView txtTask;
    public TextView txtService;
    public ImageView editBtn;
    public ImageView deleteBtn;
    LinearLayout parentLayout;
    LinearLayout taskLayout;

    public viewHolder(@NonNull View itemView) {
        super(itemView);

        nicknameText = itemView.findViewById(R.id.nicknameText);
        ymmText = itemView.findViewById(R.id.ymmText);
        crText = itemView.findViewById(R.id.crText);
        txtTask = itemView.findViewById(R.id.txttobecompleted);
        txtService = itemView.findViewById(R.id.txtwhereservice);
        image2 = itemView.findViewById(R.id.imageView2);
        image3 = itemView.findViewById(R.id.imageCompany);
        editBtn = itemView.findViewById(R.id.editBtn);
        deleteBtn = itemView.findViewById(R.id.deleteBtn);
        parentLayout = itemView.findViewById(R.id.parentid);
        taskLayout = itemView.findViewById(R.id.mainid);

    }
}
