package com.example.phosho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import Adapter.imageAdapter;
import Model.imageModel;

public class ProfileActivity extends AppCompatActivity {

    int image[] = {R.drawable.monkey, R.drawable.monkey2, R.drawable.monkey3, R.drawable.monkey4};
    GridView gridView;
    ArrayList<imageModel> arrayList;

    ImageView profilePic;
    TextView name;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = findViewById(R.id.imageview_profile);
        name = findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser != null) {
            Glide.with(ProfileActivity.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(profilePic);

            name.setText(firebaseUser.getDisplayName());
        }


    }

}