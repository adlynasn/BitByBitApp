package com.example.bitbybit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class adminPostPage extends Fragment {

    public adminPostPage() {
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
        return inflater.inflate(R.layout.fragment_admin_post_page, container, false);
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String name=bundle.getString("username");
        bundle.putString("username", name);

        Button btnBackToAdminHP = view.findViewById(R.id.returnToAdminHomePageButton);
        View.OnClickListener OCLBackAdminHP = v -> Navigation.findNavController(view).navigate(R.id.adminHomePage, bundle);
        btnBackToAdminHP.setOnClickListener(OCLBackAdminHP);

        Button BtnChoosePic = view.findViewById(R.id.choosePictureButton);
        View.OnClickListener OCLBtnChoosePic = v -> {
            // Link to Gallery
        };
        BtnChoosePic.setOnClickListener(OCLBtnChoosePic);

        Button btnAdminPublish = view.findViewById(R.id.publishPostButton);
        View.OnClickListener OCLAdminPublish = v -> {

            Toast.makeText(getContext(),"The post has been published",Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.adminHomePage, bundle);
        };
        btnAdminPublish.setOnClickListener(OCLAdminPublish);

    }
}

/*
TODO push post data to db
- when publish is pressed
- take data and push to db
- display notification that post was successfully pushed
- then go to home page
 */