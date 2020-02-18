package com.dcodestar.social;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;


public class PostAdapter extends FirestoreRecyclerAdapter<Post, PostAdapter.blogViewHolder> {
    String uid;
    PostAdapter(FirestoreRecyclerOptions<Post> options,String uid) {
        super(options);
        this.uid=uid;
    }

    @Override
    protected void onBindViewHolder(@NonNull blogViewHolder holder, int position, @NonNull final Post model) {
        holder.thumbnailImageView.setVisibility(View.VISIBLE);
        holder.nameTextView.setText(model.getUserName());
        holder.blogTextView.setText(model.blog);
        try {
            Picasso.get().load(model.getUri()).placeholder(R.drawable.userdefault).into(holder.thumbnailImageView);
        } catch (Exception e) {}
        if(uid!=null&&uid.equals(model.getUid())){
            holder.deleteme.setVisibility(View.VISIBLE);
            holder.deleteme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPost.deleteBlog(model.timestamp);
                }
            });
        }else{
            holder.deleteme.setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    @Override
    public blogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blogitem, parent, false);
        return new blogViewHolder(view);
    }

    static class blogViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView = null;
        TextView nameTextView = null;
        Button deleteme=null;
        TextView blogTextView = null;

        public blogViewHolder(View view) {
            super(view);
            thumbnailImageView = view.findViewById(R.id.thumbnailImageView);
            nameTextView = view.findViewById(R.id.userTextView);
            blogTextView = view.findViewById(R.id.blogTextView);
            deleteme=view.findViewById(R.id.deleteButton);
        }

    }
}
