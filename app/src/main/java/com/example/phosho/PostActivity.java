package com.example.phosho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference mStorageReference;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    private Button choose;

    ImageView close, image;
    TextView post;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close = findViewById(R.id.close);
        image = findViewById(R.id.image_added);
        post = findViewById(R.id.text_post);
        description = findViewById(R.id.description);
        choose = findViewById(R.id.button_choose);

        mStorageReference = FirebaseStorage.getInstance().getReference("Posts");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, MainActivity.class));
                finish();
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooser();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(PostActivity.this, "Upload in progress... ", Toast.LENGTH_LONG).show();
                } else
                    fileUploader();
            }
        });

    }

    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void fileUploader() {
        StorageReference storageReference = mStorageReference.child(System.currentTimeMillis()+"."+getExtension(imageUri));

        uploadTask = storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        startActivity(new Intent(PostActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(PostActivity.this, "Failed!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/^");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this, MainActivity.class));
            finish();
        }
    }
}