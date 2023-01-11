package com.example.bitbybit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

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

            EditText postTitle = view.findViewById(R.id.postTitleName);
            EditText postDescription = view.findViewById(R.id.postDescription);
            ImageView postImage = view.findViewById(R.id.PostPicture);

            AtomicReference<Boolean> status = new AtomicReference<>(false);

            Thread dataThread = new Thread(() -> {
                try {
                    Connection connection = Line.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO post (post_title, post_description) VALUES ('"
                            + postTitle.getText().toString() + "', '"
                            + postDescription.getText().toString() + "')");

                    if (postTitle.getText().toString().equals("") || postDescription.getText().toString().equals(""))
                        status.set(true);

                    if (!status.get()) {
                        preparedStatement.executeUpdate();
                        requireActivity().runOnUiThread(() -> Navigation.findNavController(view).navigate(R.id.adminHomePage, bundle));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dataThread.start();
            while (dataThread.isAlive()){

            }

            if (status.get())
                Toast.makeText(getContext(),"Please fill all the info!",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(),"The post has been published",Toast.LENGTH_SHORT).show();

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