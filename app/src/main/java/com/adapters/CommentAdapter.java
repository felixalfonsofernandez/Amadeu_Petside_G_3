package com.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registrarse.Comment;
import com.example.registrarse.Post;
import com.example.registrarse.PostDetailActivity;
import com.example.registrarse.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.providers.AuthProvider;
import com.providers.CommentsProvider;
import com.providers.UsersProvider;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends FirestoreRecyclerAdapter<Comment, CommentAdapter.ViewHolder> {
    Context context;
    UsersProvider mUsersProvider;
    AuthProvider mAuthProvider;
    CommentsProvider mCommentsProvider;

    public CommentAdapter(FirestoreRecyclerOptions<Comment> options, Context context){
        super(options);
        this.context = context;
        mUsersProvider = new UsersProvider();
        mAuthProvider = new AuthProvider();
        mCommentsProvider = new CommentsProvider();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Comment comment) {
        DocumentSnapshot document = getSnapshots().getSnapshot(position);
        final String commentId = document.getId();
        String idUser = document.getString("idUser");
        holder.textViewComment.setText(comment.getComment());
        getUserInfo(idUser,holder);

        if (idUser.equals(mAuthProvider.getUid())) {
            holder.imageViewDeleteMyComment.setVisibility(View.VISIBLE);
            holder.imageViewEditMyComment.setVisibility(View.VISIBLE);
        }
        else {
            holder.imageViewDeleteMyComment.setVisibility(View.GONE);
            holder.imageViewEditMyComment.setVisibility(View.GONE);
        }


        holder.imageViewDeleteMyComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewDeleteMyComment(commentId);
            }
        });

        holder.imageViewEditMyComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewEditMyComment(commentId);
            }
        });
    }

    private void imageViewDeleteMyComment(String commentId) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Eliminar comentario")
                .setMessage("¿Estas seguro de realizar esta accion?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletemycomment(commentId);
                    }
                })
                .setNegativeButton("NO", null)
                .show();
    }

    private void imageViewEditMyComment(String commentId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("¡COMENTARIO!");
        alert.setMessage("Ingresa tu comentario");

        final EditText editText = new EditText(context);
        editText.setHint("Texto");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(36, 0, 36, 36);
        editText.setLayoutParams(params);
        RelativeLayout container = new RelativeLayout(context);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        container.setLayoutParams(relativeParams);
        container.addView(editText);

        alert.setView(container);


        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String value = editText.getText().toString();
                HashMap hashMap = new HashMap();
                if (!value.isEmpty()) {
                    hashMap.put("comment",value);
                    editemycomment(commentId,hashMap);
                }
                else {
                    Toast.makeText(context, "Debe ingresar el comentario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.show();
    }


    private void deletemycomment(String idComment) {
        mCommentsProvider.delete(idComment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "El comentario se elimino correctamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "No se pudo eliminar el post", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editemycomment(String idComment,HashMap comment) {
        mCommentsProvider.update(idComment,comment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "El post se elimino correctamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "No se pudo eliminar el post", Toast.LENGTH_SHORT).show();
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
                        holder.textViewUsername.setText(username.toUpperCase());
                    }
                    if(documentSnapshot.contains("image_profile")){
                        String imageProfile = documentSnapshot.getString("image_profile");
                        if(imageProfile!=null){
                            if(!imageProfile.isEmpty()){
                                Picasso.with(context).load(imageProfile).into(holder.circleImageComment);
                            }
                        }
                    }
                }
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_comment,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewUsername;
        TextView textViewComment;
        CircleImageView circleImageComment;
        View viewHolder;
        ImageView imageViewDeleteMyComment;
        ImageView imageViewEditMyComment;

        public ViewHolder(View view){
            super(view);
            textViewUsername = view.findViewById(R.id.textViewUsername);
            textViewComment = view.findViewById(R.id.textViewComment);
            circleImageComment = view.findViewById(R.id.circleImageComment);
            imageViewDeleteMyComment = view.findViewById(R.id.imageViewDeleteMyComment);
            imageViewEditMyComment = view.findViewById(R.id.imageViewEditMyComment);
            viewHolder = view;
        }

    }




}