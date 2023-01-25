package com.example.bitbybit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminFoodDetail extends Fragment {

    public AdminFoodDetail() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_food_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnBackRecipeFoodDetails = view.findViewById(R.id.returnToAdminHomePageButton);
        View.OnClickListener OCLBackAdminHP = v -> Navigation.findNavController(view).navigate(R.id.adminHomePage);
        btnBackRecipeFoodDetails.setOnClickListener(OCLBackAdminHP);

        Button btnDeleteAdminFoodDetails = view.findViewById(R.id.deleteAdminRecipeButton);
        View.OnClickListener OCLAdminDelete = v -> Navigation.findNavController(view).navigate(R.id.adminHomePage);
        btnDeleteAdminFoodDetails.setOnClickListener(OCLAdminDelete);
    }
}