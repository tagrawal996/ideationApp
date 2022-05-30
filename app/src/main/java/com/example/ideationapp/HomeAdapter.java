package com.example.ideationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.Precondition;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<PostModel> posts = new ArrayList<>();
    private Context context;
    FirebaseFirestore fstore;

    public HomeAdapter(List<PostModel> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        fstore = FirebaseFirestore.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.home_item,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        //final boolean[] ishit = {false};
        PostModel post = posts.get(position);
        //final int[] count = {post.getHitCount()};
        holder.userName.setText(post.getUsername());
        holder.profession.setText(post.getProfession());
        holder.time.setText(post.getTime());
        holder.overView.setText(post.getOverview());
        holder.description.setText(post.getDescription());
        holder.likecount.setText(""+ post.getHitCount());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private TextView userName, profession, time, overView, description, likecount;
        private ImageView profileImage;
        private ImageButton like, bookmark;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.postUserName);
            profession = itemView.findViewById(R.id.postProfession);
            time = itemView.findViewById(R.id.postTime);
            overView = itemView.findViewById(R.id.PostOverView);
            description = itemView.findViewById(R.id.postdescription);
            profileImage = itemView.findViewById(R.id.postProfile);
            like = itemView.findViewById(R.id.postLike);
            bookmark = itemView.findViewById(R.id.postBookmark);
            likecount = itemView.findViewById(R.id.hitcount);
        }


    }
}
