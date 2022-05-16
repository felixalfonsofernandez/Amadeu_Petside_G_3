package com.providers;

import com.example.registrarse.Comment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class CommentsProvider {

    CollectionReference mCollection;

    public CommentsProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("Comments");
    }

    public Task<Void> create(Comment comment) {
        return mCollection.document().set(comment);
    }

    public Query getCommentsByPost(String idPost) {
        return mCollection.whereEqualTo("idPost", idPost);
    }

    public Task<Void> delete(String id) {
        return mCollection.document(id).delete();
    }

    public Task<Void> update(String id, HashMap comment) {
        return mCollection.document(id).update(comment);
    }

    public Task <DocumentSnapshot> getCommentById(String id){
        return mCollection.document(id).get();
    }
}