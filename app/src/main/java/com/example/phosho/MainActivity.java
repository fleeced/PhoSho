package com.example.phosho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.example.phosho.Fragment.SearchFragment;
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

    LinearLayout profile, updates, notifications, search, settings;
    ImageView profilePic;
    TextView name;
    Button signoutButton, postButton;
    Fragment selectedFragment;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivities();

        profilePic = findViewById(R.id.imageview_profile);
        name = findViewById(R.id.username);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser != null) {
            Glide.with(MainActivity.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(profilePic);

            name.setText(firebaseUser.getDisplayName());
        }

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);
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
                //openSearchActivity();
                selectedFragment = new SearchFragment();
            }
        });
        settings = (LinearLayout) findViewById(R.id.menu_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

        signoutButton = findViewById(R.id.button_signout);
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
        postButton = findViewById(R.id.button_post);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postActivity();
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
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }
    public void openSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    public void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void postActivity() {
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

}