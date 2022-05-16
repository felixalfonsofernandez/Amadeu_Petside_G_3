package com.example.registrarse;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.adapters.PostAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.providers.PostProvider;


public class Adopcion extends AppCompatActivity {

    RecyclerView mRecyclerView;
    PostProvider mPostProvider;
    PostAdapter mPostsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopcion);
        mRecyclerView = findViewById(R.id.recyclerViewHome);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Adopcion.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mPostProvider = new PostProvider();
    }

    @Override
    public void onStart(){
        super.onStart();
        Query query = mPostProvider.getAdopcion();
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post.class).build();
        mPostsAdapter = new PostAdapter(options, this);
        mRecyclerView.setAdapter(mPostsAdapter);
        mPostsAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        mPostsAdapter.stopListening();
    }


    public void Chloe (View view){
        Intent felino = new Intent(this, wq.class);
        startActivity(felino);
    }

    public void qe (View view){
        Intent canino = new Intent(this, qe.class);
        startActivity(canino);
    }
    public void Felino (View view){
        Intent felinoo = new Intent(this, dada.class);
        startActivity(felinoo);
    }

    public void Caninoo (View view){
        Intent caninoo = new Intent(this, qr.class);
        startActivity(caninoo);
    }
    public void Felinoo (View view){
        Intent felinooo = new Intent(this, dadu.class);
        startActivity(felinooo);
    }

    public void Canino (View view){
        Intent caninooo = new Intent(this, qt.class);
        startActivity(caninooo);
    }
    public void Caninooo (View view){
        Intent caninoooo = new Intent(this, qq.class);
        startActivity(caninoooo);
    }
}