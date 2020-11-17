package com.example.phosho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Model.Users;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;
    private FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.button_search);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);
            }
        });
    }

    private void firebaseUserSearch(String searchText) {
        firebaseRecyclerAdapter.startListening();
        mResultList.setAdapter(firebaseRecyclerAdapter);
        //    DatabaseReference personsRef = mUserDatabase.child("People");
        //    Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");
        Query personsQuery = mUserDatabase.orderByKey();

        mResultList.hasFixedSize();
        mResultList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions personsOptions = new FirebaseRecyclerOptions.Builder<Users>().setQuery(personsQuery, Users.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(personsOptions
        ) {
            @Override
            protected void onBindViewHolder(UsersViewHolder holder, int position, Users model) {
                   holder.setDetails(model.getFullname(), model.getBio(), model.getImageurl());
            }

            @Override
            public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_layout, parent, false);

                return new UsersViewHolder(view);
            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);
    }

        /*
        private void firebaseUserSearch(String searchText) {

            Toast.makeText(SearchActivity.this, "Clicked button", Toast.LENGTH_LONG).show();

            Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
                    Users.class,
                    R.layout.list_layout,
                    UsersViewHolder.class,
                    mUserDatabase
            ) {
                @NonNull
                @Override
                public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    return null;
                }

                @Override
                protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {

                }

                @Override
                protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {
                    viewHolder.setDetails(model.getName(), model. getStatus(), model.getImage());
                }
            };

            mResultList.setAdapter(firebaseRecyclerAdapter);

        }

         */

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDetails(String name, String status, String image) {
            TextView user_name = (TextView) mView.findViewById(R.id.username);
            TextView user_description = (TextView) mView.findViewById(R.id.search_description);
            ImageView user_image = (ImageView) mView.findViewById(R.id.imageview_profile);

            user_name.setText(name);
            user_description.setText(status);

            Glide.with(getApplicationContext()).load(image).into(user_image);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

}