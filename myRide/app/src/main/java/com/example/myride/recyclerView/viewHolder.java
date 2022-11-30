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
    public TextView nicknameText;
    public TextView ymmText;
    public TextView crText;
    Button editBtn;
    public Button deleteBtn;
    LinearLayout parentLayout;

    public viewHolder(@NonNull View itemView) {
        super(itemView);

        nicknameText = itemView.findViewById(R.id.nicknameText);
        ymmText = itemView.findViewById(R.id.ymmText);
        crText = itemView.findViewById(R.id.crText);
        image2 = itemView.findViewById(R.id.imageView2);
        editBtn = itemView.findViewById(R.id.editBtn);
        deleteBtn = itemView.findViewById(R.id.deleteBtn);
        parentLayout = itemView.findViewById(R.id.parentid);

//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (!validateForm()) {
////                    return;
////                }
//
//            }
//        });
    }
}
