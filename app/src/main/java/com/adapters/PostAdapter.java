package com.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.registrarse.Like;
import com.example.registrarse.Post;
import com.example.registrarse.PostDetailActivity;
import com.example.registrarse.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.providers.AuthProvider;
import com.providers.LikesProvider;
import com.providers.PostProvider;
import com.providers.UsersProvider;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class PostAdapter extends FirestoreRecyclerAdapter<Post,PostAdapter.ViewHolder> {
    Context context;
    UsersProvider mUsersProvider;
    LikesProvider mLikesProvider;
    AuthProvider mAuthProvider;

    public PostAdapter(FirestoreRecyclerOptions<Post> options,Context context){
        super(options);
        this.context = context;
        mUsersProvider = new UsersProvider();
        mLikesProvider = new LikesProvider();
        mAuthProvider = new AuthProvider();
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final Post post) {
        DocumentSnapshot document = getSnapshots().getSnapshot(position);
        final String postId = document.getId();
        holder.textViewTitle.setText(post.getTittle().toUpperCase());
        holder.textViewDescription.setText(post.getDescription());
        if(post.getImage1() != null){
            if(!post.getImage1().isEmpty()){
                Picasso.with(context).load(post.getImage1()).into(holder.imageViewPost);
            }
        }
        holder.viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("id",postId);
                context.startActivity(intent);
            }
        });

        holder.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Like like = new Like();
                like.setIdUser(mAuthProvider.getUid());
                like.setIdPost(postId);
                like.setTimestamp(new Date().getTime());
                like(like, holder);
            }
        });

        getUserInfo(post.getIdUser(), holder);
        getNumberLikesByPost(postId,holder);
        checkIfExistLike(postId,mAuthProvider.getUid(),holder);
        //Picasso.with(context).load(post.getImage1()).into(holder.imageViewPost );
    }

    private void getNumberLikesByPost(String idPost, final ViewHolder holder){
        mLikesProvider.getLikesByPost(idPost).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    int numberLikes = queryDocumentSnapshots.size();
                    holder.textViewLikes.setText(String.valueOf(numberLikes) + " Me encantas");
                }
            }
        });
    }



    private void like(final Like like, ViewHolder holder){

        mLikesProvider.getLikeByPostAndUser(like.getIdPost(),mAuthProvider.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int numberDocuments = queryDocumentSnapshots.size();
                if(numberDocuments > 0 ){
                    String idLike = queryDocumentSnapshots.getDocuments().get(0).getId();
                    holder.imageViewLike.setImageResource(R.drawable.ic_heart);
                    mLikesProvider.delete(idLike);
                }
                else {
                    holder.imageViewLike.setImageResource(R.drawable.ic_favorite);
                    mLikesProvider.create(like);
                }
            }
        });


    }


    private void checkIfExistLike(String idPost,String idUser, ViewHolder holder){

        mLikesProvider.getLikeByPostAndUser(idPost, idUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int numberDocuments = queryDocumentSnapshots.size();
                if(numberDocuments > 0 ){
                    holder.imageViewLike.setImageResource(R.drawable.ic_favorite);
                }
                else {
                    holder.imageViewLike.setImageResource(R.drawable.ic_heart);
                }
            }
        });


    }

    private void getUserInfo(String idUser,final ViewHolder holder){
        mUsersProvider.getUser(idUser).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    if(documentSnapshot.contains("username")){
                        String username = documentSnapshot.getString("username");
                        holder.textViewUsername.setText("By: "+ username);
                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewUsername;
        TextView textViewLikes;
        ImageView imageViewLike;
        ImageView imageViewPost;
        View viewHolder;

        public ViewHolder(View view){
            super(view);
            textViewTitle = view.findViewById(R.id.textViewTitlePostCard);
            textViewDescription = view.findViewById(R.id.textViewDescriptionPostCard);
            textViewUsername = view.findViewById(R.id.textViewUsernamePostCard);
            textViewLikes = view.findViewById(R.id.textViewLikes);
            imageViewPost = view.findViewById(R.id.imageViewPostCard);
            imageViewLike = view.findViewById(R.id.imageViewLike);
            viewHolder = view;
        }

    }




}
