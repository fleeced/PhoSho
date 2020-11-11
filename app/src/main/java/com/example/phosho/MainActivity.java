package com.example.phosho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import static androidx.core.os.HandlerCompat.postDelayed;

public class MainActivity extends AppCompatActivity {

    private LinearLayout profile, updates, notifications, search, settings;
    private ImageView profilePic;
    private TextView name;
    private Button signoutButton;

    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivities();

        profilePic = findViewById(R.id.imageview_profile);
        name = findViewById(R.id.username);
        signoutButton = findViewById(R.id.button_signout);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser != null) {
            Glide.with(MainActivity.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(profilePic);

            name.setText(firebaseUser.getDisplayName());
        }

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            mAuth.signOut();
                            Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
    }

    public void getActivities() {
        profile = (LinearLayout) findViewById(R.id.menu_myProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });
        updates = (LinearLayout) findViewById(R.id.menu_updates);
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdatesActivity();
            }
        });
        notifications = (LinearLayout) findViewById(R.id.menu_notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotificationsActivity();
            }
        });
        search = (LinearLayout) findViewById(R.id.menu_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });
        settings = (LinearLayout) findViewById(R.id.menu_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

    }

    public void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    public void openUpdatesActivity() {
        Intent intent = new Intent(this, UpdatesActivity.class);
        startActivity(intent);
    }
    public void openNotificationsActivity() {
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }
    public void openSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    public void openSettingsActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

}