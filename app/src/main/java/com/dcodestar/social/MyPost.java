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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MyPost extends Fragment {
    static PostAdapter postAdapter;
    private static final String TAG = "MyPost";
    public MyPost() {
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
        RecyclerView blogRecyclerView=view.findViewById(R.id.blogRecyclerView);
        blogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("blogs")
                .whereEqualTo("uid",firebaseUser.getUid())
                .orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .build();
        postAdapter=new PostAdapter(options,firebaseUser.getUid());
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


    static void deleteBlog(final String timeStamp){
        Log.d("deleting", "deleteBlog: inside deleteblog");
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore.collection("blogs").whereEqualTo("uid",firebaseUser.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()){
                            String time=documentSnapshot.getData().get("timestamp").toString();
                            Log.d("item",time+":"+timeStamp);
                            if(timeStamp.equals(time)){
                                documentSnapshot.getReference().delete();
                                MyPost.postAdapter.stopListening();
                                MyPost.postAdapter.startListening();
                                break;
                            }
                        }
                    }
                });
    }

}
