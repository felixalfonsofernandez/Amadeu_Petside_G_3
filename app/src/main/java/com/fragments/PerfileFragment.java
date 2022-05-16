package com.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adapters.MyPostsAdapter;
import com.example.registrarse.EditProfileActivity;
import com.example.registrarse.EditProfileMascotaActivity;
import com.example.registrarse.MainActivity;
import com.example.registrarse.Post;
import com.example.registrarse.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.providers.PostProvider;
import com.providers.UsersProvider;
import com.providers.AuthProvider;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfileFragment extends Fragment {

    View mView;
    LinearLayout mLinearLayoutEditProfile;
    LinearLayout mLinearLayoutEditProfileMascota;

    TextView mTextViewUsername;
    TextView mTextViewPhone;
    TextView mTextViewEmail;
    TextView mTextViewPostNumber;
    TextView mTextViewPostExist;
    TextView mTextNombrePerrito;
    TextView mTextEdadPerrito;
    TextView mTextDescripcionPerrito;
    ImageView mImageViewCover;
    CircleImageView mCircleImageProfile;
    RecyclerView mRecyclerView;

    UsersProvider mUsersProvider;
    AuthProvider mAuthProvider;
    PostProvider mPostProvider;

    MyPostsAdapter mAdapter;

    Button btnsalir;

    public PerfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_perfile, container, false);
        mLinearLayoutEditProfile = mView.findViewById(R.id.linearLayoutEditProfile);
        mLinearLayoutEditProfileMascota = mView.findViewById(R.id.linearLayoutEditProfileMascota);
        mTextViewEmail = mView.findViewById(R.id.textViewEmail);
        mTextViewUsername = mView.findViewById(R.id.textViewUsername);
        mTextViewPhone = mView.findViewById(R.id.textViewphone);
        mTextViewPostNumber = mView.findViewById(R.id.textViewPostNumber);
        mTextViewPostExist = mView.findViewById(R.id.textViewPostExist);
        mCircleImageProfile = mView.findViewById(R.id.circleImageProfile);
        mTextNombrePerrito = mView.findViewById(R.id.nombreperrito);
        mTextEdadPerrito = mView.findViewById(R.id.edadperrito);
        mImageViewCover = mView.findViewById(R.id.imageViewCover);
        mTextDescripcionPerrito = mView.findViewById(R.id.descripcionmascota);
        mRecyclerView = mView.findViewById(R.id.recyclerViewMyPost);

        btnsalir=mView.findViewById(R.id.btsalir);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mLinearLayoutEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditProfile();
            }
        });

        mLinearLayoutEditProfileMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditProfileMascota();
            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                com.facebook.login.LoginManager.getInstance().logOut();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        mUsersProvider = new UsersProvider();
        mAuthProvider = new AuthProvider();
        mPostProvider = new PostProvider();

        getUser();
        getPostNumber();
        checkIfExistPost();
        return mView;

    }

    private void checkIfExistPost() {
        mPostProvider.getPostByUser(mAuthProvider.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots != null){
                int numberPost = queryDocumentSnapshots.size();
                if (numberPost > 0) {
                    mTextViewPostExist.setText("Publicaciones");
                    mTextViewPostExist.setTextColor(Color.RED);
                }
                else {
                    mTextViewPostExist.setText("No hay publicaciones");
                    mTextViewPostExist.setTextColor(Color.GRAY);
                }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = mPostProvider.getPostByUser(mAuthProvider.getUid());
        FirestoreRecyclerOptions<Post> options =
                new FirestoreRecyclerOptions.Builder<Post>()
                        .setQuery(query, Post.class)
                        .build();
        mAdapter = new MyPostsAdapter(options, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


    private void getPostNumber() {
        mPostProvider.getPostByUser(mAuthProvider.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int numberPost = queryDocumentSnapshots.size();
                mTextViewPostNumber.setText(String.valueOf(numberPost));
            }
        });
    }


    private void getUser() {
        mUsersProvider.getUser(mAuthProvider.getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.contains("email")) {
                        String email = documentSnapshot.getString("email");
                        mTextViewEmail.setText(email);
                    }
                    if (documentSnapshot.contains("fon")) {
                        String phone = documentSnapshot.getString("fon");
                        mTextViewPhone.setText(phone);
                    }
                    if (documentSnapshot.contains("mascota")) {
                        String mascota = documentSnapshot.getString("mascota");
                        mTextNombrePerrito.setText(mascota);
                    }
                    if (documentSnapshot.contains("username")) {
                        String username = documentSnapshot.getString("username");
                        mTextViewUsername.setText(username.toUpperCase());
                    }
                    if (documentSnapshot.contains("descripcionmascota")){
                        String descripcionmascota = documentSnapshot.getString("descripcionmascota");
                        mTextDescripcionPerrito.setText(descripcionmascota);
                    }
                    if(documentSnapshot.contains("edadmascota")){
                        String edadmascota = documentSnapshot.getString("edadmascota");
                        mTextEdadPerrito.setText(edadmascota);
                    }
                    if (documentSnapshot.contains("imageProfile")) {
                        String imageProfile = documentSnapshot.getString("imageProfile");
                        if (imageProfile != null) {
                            if (!imageProfile.isEmpty()) {
                                Picasso.with(getContext()).load(imageProfile).into(mCircleImageProfile);
                            }
                        }
                    }
                    if (documentSnapshot.contains("imageCover")) {
                        String imageCover = documentSnapshot.getString("imageCover");
                        if (imageCover != null) {
                            if (!imageCover.isEmpty()) {
                                Picasso.with(getContext()).load(imageCover).into(mImageViewCover);
                            }
                        }
                    }
                }
            }
        });
    }

    private void goToEditProfile() {
        Intent intent = new Intent(getContext(), EditProfileActivity.class);
        startActivity(intent);
    }

    private void goToEditProfileMascota() {
        Intent intent = new Intent(getContext(), EditProfileMascotaActivity.class);
        startActivity(intent);
    }
}