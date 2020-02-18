package com.dcodestar.social;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AllPost extends Fragment {

    PostAdapter postAdapter;
    private static final String TAG = "AllPost";
    public AllPost() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: inside fragment");
        View view =inflater.inflate(R.layout.fragment_all_post, container, false);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        RecyclerView blogRecyclerView=view.findViewById(R.id.blogRecyclerView);
        blogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("blogs")
                .orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .build();
        postAdapter=new PostAdapter(options,"");
        blogRecyclerView.setAdapter(postAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(postAdapter!=null){
            postAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(postAdapter!=null){
            postAdapter.stopListening();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
