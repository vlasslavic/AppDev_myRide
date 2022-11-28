package com.example.myride.fragments;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myride.AddCar;
import com.example.myride.Dashboard;
import com.example.myride.R;
import com.example.myride.models.userModel;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mainCar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainCar extends Fragment implements  View.OnClickListener {

    Button addCarBtn;
    TextView textView;
    userModel user;
    String mainCar;
    Bundle bundle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public mainCar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainCar.
     */
    // TODO: Rename and change types and number of parameters
    public static mainCar newInstance(String param1, String param2) {
        mainCar fragment = new mainCar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            addCarBtn = getActivity().findViewById(R.id.addCarBtn);
            textView = getActivity().findViewById(R.id.textView);

            bundle = this.getArguments();
            mainCar = bundle.getString("mainCar"); // Key
            if(mainCar==null){
                addCarBtn.setVisibility(VISIBLE);
                textView.setVisibility(INVISIBLE);
            }else{
                addCarBtn.setVisibility(INVISIBLE);
                textView.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_main_car, container, false);
    }



    public void changeData(userModel data) {
        user.setMainCar(data.getMainCar());
        user.setMyGarage(data.getMyGarage());
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), AddCar.class);
        startActivity(i);
    }

    public void addValueEventListener(ValueEventListener userListener) {
    }
}