package com.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.registrarse.ChatActivity;
import com.example.registrarse.Message;
import com.example.registrarse.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.providers.AuthProvider;
import com.providers.MessagesProvider;
import com.providers.UsersProvider;
import com.squareup.picasso.Picasso;
import com.utils.RelativeTime;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends FirestoreRecyclerAdapter<Message, MessagesAdapter.ViewHolder> {

    Context context;
    UsersProvider mUsersProvider;
    AuthProvider mAuthProvider;
    MessagesProvider mMessagesProvider;

    public MessagesAdapter(FirestoreRecyclerOptions<Message> options, Context context) {
        super(options);
        this.context = context;
        mUsersProvider = new UsersProvider();
        mAuthProvider = new AuthProvider();
        mMessagesProvider = new MessagesProvider();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull final Message message) {
        DocumentSnapshot document = getSnapshots().getSnapshot(position);
        final String messageId = document.getId();
        holder.textViewMessage.setText(message.getMessage());
        String idUser = document.getString("idSender");

        String relativeTime = RelativeTime.timeFormatAMPM(message.getTimestamp(), context);
        holder.textViewDate.setText(relativeTime);

        if (message.getIdSender().equals(mAuthProvider.getUid())) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.setMargins(150, 0, 0,0);
            holder.linearLayoutMessage.setLayoutParams(params);
            holder.linearLayoutMessage.setPadding(30, 20, 0, 20);
            holder.linearLayoutMessage.setBackground(context.getResources().getDrawable(R.drawable.rounded_linear_layout));
            holder.imageViewViewed.setVisibility(View.VISIBLE);
            holder.textViewMessage.setTextColor(Color.BLACK);
            holder.textViewMessage.setTextColor(Color.BLACK);
        }
        else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.setMargins(0, 0, 150,0);
            holder.linearLayoutMessage.setLayoutParams(params);
            holder.linearLayoutMessage.setPadding(30, 20, 30, 20);
            holder.linearLayoutMessage.setBackground(context.getResources().getDrawable(R.drawable.rounded_linear_layout_grey));
            holder.imageViewViewed.setVisibility(View.GONE);
            holder.textViewMessage.setTextColor(Color.BLACK);
            holder.textViewMessage.setTextColor(Color.BLACK);
        }

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
                imageViewDeleteMyMessage(messageId);
            }
        });

        holder.imageViewEditMyComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewEditMyMessage(messageId);
            }
        });

        if (message.isViewed()) {
            holder.imageViewViewed.setImageResource(R.drawable.icon_check_blue);
        }
        else {
            holder.imageViewViewed.setImageResource(R.drawable.icon_check_grey);
        }

    }

    private void imageViewDeleteMyMessage(String messageId) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Eliminar comentario")
                .setMessage("¿Estas seguro de realizar esta accion?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletemymessage(messageId);
                    }
                })
                .setNegativeButton("NO", null)
                .show();
    }

    private void imageViewEditMyMessage(String messageId) {
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
                    hashMap.put("message",value);
                    editemymessage(messageId,hashMap);
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

    private void deletemymessage(String messageId) {
        mMessagesProvider.delete(messageId).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private void editemymessage(String messageId,HashMap comment) {
        mMessagesProvider.update(messageId,comment).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    private void goToChatActivity(String chatId, String idUser1, String idUser2) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("idChat", chatId);
        intent.putExtra("idUser1", idUser1);
        intent.putExtra("idUser2", idUser2);
        context.startActivity(intent);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_message, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;
        TextView textViewDate;
        ImageView imageViewViewed;
        LinearLayout linearLayoutMessage;
        ImageView imageViewDeleteMyComment;
        ImageView imageViewEditMyComment;
        View viewHolder;

        public ViewHolder(View view) {
            super(view);
            textViewMessage = view.findViewById(R.id.textViewMessage);
            textViewDate = view.findViewById(R.id.textViewDateMessage);
            imageViewViewed = view.findViewById(R.id.imageViewViewedMessage);
            linearLayoutMessage = view.findViewById(R.id.linearLayoutMessage);
            imageViewDeleteMyComment = view.findViewById(R.id.imageViewDeleteMyComment);
            imageViewEditMyComment = view.findViewById(R.id.imageViewEditMyComment);
            viewHolder = view;
        }
    }

}

