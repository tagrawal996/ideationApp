package com.example.ideationapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideationapp.CommentPage;
import com.example.ideationapp.Model.PostModel;
import com.example.ideationapp.Model.userModel;
import com.example.ideationapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.HomeViewHolder> {

    private List<PostModel> posts = new ArrayList<>();
    private Context context;
    FirebaseFirestore fstore;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    boolean toshow;

    public PostAdapter(List<PostModel> posts, Context context, boolean toshow) {
        this.posts = posts;
        this.context = context;
        this.toshow = toshow;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        fstore = FirebaseFirestore.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.post_item,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        PostModel post = posts.get(position);

        holder.overView.setText(post.getOverview());
        holder.description.setText(post.getDescription());
        FirebaseDatabase.getInstance().getReference().child("Users").
                child(post.getUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel user = snapshot.getValue(userModel.class);
                holder.profession.setText(user.getProfession());
                holder.userName.setText(user.getUserName());
                if (user.getImageURL().equals("default")){
                    holder.profileImage.setImageResource(R.drawable.avatar);
                }
                else Picasso.get().load(user.getImageURL()).into(holder.profileImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.time.setText(post.getTime());

        isLiked(post.getPostUrl(),holder.like,holder.likecount);

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentPage.class);
                intent.putExtra("postId",post.getPostUrl());
                intent.putExtra("authorId",post.getUserID());
            }
        });

        getCommentCount(post.getPostUrl(),holder.commentcount);

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.like.getTag().equals(0)){
                    FirebaseDatabase.getInstance().getReference().child("likes").child(post.getPostUrl()).
                            child(uid).setValue(true);
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("likes").child(post.getPostUrl()).child(uid).removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private TextView userName, profession, overView, description;
        private TextView likecount,commentcount,savecount,time;
        private ImageView profileImage;
        private ImageButton like, bookmark, delete, comment;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.postUserName);
            profession = itemView.findViewById(R.id.postProfession);
            time = itemView.findViewById(R.id.postTime);
            overView = itemView.findViewById(R.id.PostOverView);
            description = itemView.findViewById(R.id.postdescription);
            profileImage = itemView.findViewById(R.id.postProfile);
            like = itemView.findViewById(R.id.postLike);
            comment = itemView.findViewById(R.id.post_comment);
            bookmark = itemView.findViewById(R.id.post_bookmark);
            likecount = itemView.findViewById(R.id.likecount);
            commentcount = itemView.findViewById(R.id.commentCount);
            savecount = itemView.findViewById(R.id.shaveCount);
            delete = itemView.findViewById(R.id.deleteButton);
        }


    }

    private void isLiked(String postId, ImageButton button, TextView likecount){
        FirebaseDatabase.getInstance().getReference().child("likes").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likecount.setText(snapshot.getChildrenCount()+" likes");
                if (snapshot.child(uid).exists()){
                    button.setTag(1);
                    button.setImageResource(R.drawable.ic_liked);
                }
                else {
                    button.setTag(0);
                    button.setImageResource(R.drawable.ic_like);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getCommentCount(String postID, TextView count){
        FirebaseDatabase.getInstance().getReference().child("comments").child(postID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count.setText(snapshot.getChildrenCount()+"Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
