package com.example.myride.recyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    Button editBtn;
    Button btnEdit;
    public Button deleteBtn;
    public Button btnDelete;
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
        btnEdit = itemView.findViewById(R.id.btnedit);
        deleteBtn = itemView.findViewById(R.id.deleteBtn);
        btnDelete = itemView.findViewById(R.id.btndelete);
        parentLayout = itemView.findViewById(R.id.parentid);
        taskLayout = itemView.findViewById(R.id.mainid);

//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (!validateForm()) {
////                    return;
////                }
//
//            }
//        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
