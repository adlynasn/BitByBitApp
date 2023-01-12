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
import android.widget.Toast;

public class adminHomePage extends Fragment {


    public adminHomePage() {
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
        return inflater.inflate(R.layout.fragment_admin_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String name=bundle.getString("username");
        bundle.putString("username", name);

        Button btnAdminPostPage = view.findViewById(R.id.adminPostPageButton);
        View.OnClickListener OCLAdminPostPage = v -> Navigation.findNavController(view).navigate(R.id.adminPostPage, bundle);
        btnAdminPostPage.setOnClickListener(OCLAdminPostPage);

        Button btnAdminUpload = view.findViewById(R.id.adminUploadRecipeButton);
        View.OnClickListener OCLUploadAdmin = v -> Navigation.findNavController(view).navigate(R.id.adminUploadRecipePage, bundle);
        btnAdminUpload.setOnClickListener(OCLUploadAdmin);

        Button btnAdminViewRep = view.findViewById(R.id.adminViewReportsButton);
        View.OnClickListener OCLViewRep = v -> Navigation.findNavController(view).navigate(R.id.adminViewReportsPage, bundle);
        btnAdminViewRep.setOnClickListener(OCLViewRep);

        Button btnLogoutAdmin = view.findViewById(R.id.adminLogoutButton);
        View.OnClickListener OCLLogOut = v -> {
            Navigation.findNavController(view).navigate(R.id.loginPage, bundle);
            Toast.makeText(getContext(),"You have log out",Toast.LENGTH_SHORT).show();
        };
        btnLogoutAdmin.setOnClickListener(OCLLogOut);
    }
}