package com.providers;

import com.example.registrarse.Post;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PostsProvider {

    CollectionReference mCollection;

    public PostsProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("Posts");
    }

    public Task<Void> save(Post post) {
        return mCollection.document().set(post);
    }

    public Query getAll() {
        return mCollection.orderBy("title", Query.Direction.DESCENDING);
    }

    public Query getPostByUser(String id) {
        return mCollection.whereEqualTo("idUser", id);
    }

    public Task<DocumentSnapshot> getPostById(String id) {
        return mCollection.document(id).get();
    }

}