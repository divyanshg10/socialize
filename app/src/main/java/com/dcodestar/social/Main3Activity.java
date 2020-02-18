package com.dcodestar.social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    private static final String TAG = "Main3Activity";
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        ImageView imageView=findViewById(R.id.userImageView);
        Picasso.get().load(firebaseUser.getPhotoUrl()).placeholder(R.drawable.ic_menu_gallery).into(imageView);
    }

    public void postClicked(View view) {
        final Button button = (Button) view;
        button.setClickable(false);
        EditText editText = findViewById(R.id.postEditText);
        String blog = editText.getText().toString();
        if (blog.length() > 0) {
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            Uri uri = firebaseUser.getPhotoUrl();
            String url;
            if (uri == null) {
                url = "";
            } else {
                url = uri.toString();
            }
            Log.d(TAG, "postClicked: uploading post");
            Post post = new Post(firebaseUser.getDisplayName(), url, blog, firebaseUser.getUid());
            firebaseFirestore.collection("blogs").add(post)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: added");
                            Toast.makeText(getApplicationContext(), "Blog Posted Successfully", Toast.LENGTH_SHORT).show();
                            Main3Activity.this.finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            button.setClickable(true);
                            Log.d(TAG, "onFailure: failed"+e.getMessage());
                            Toast.makeText(getApplicationContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            button.setClickable((true));
        }
    }
}
