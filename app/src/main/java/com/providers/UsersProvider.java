package com.providers;

import com.example.registrarse.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsersProvider {

    private CollectionReference mCollection;

    public UsersProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("Users");
    }

    public Task<DocumentSnapshot> getUser(String id) {
        return mCollection.document(id).get();
    }

    public DocumentReference getUserRealtime(String id) {
        return mCollection.document(id);
    }

    public Task<Void> create(User user){
        return mCollection.document(user.getId()).set(user);
    }

    public Task<Void> update(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("mascota", user.getMascota());
        map.put("descripcionmascota", user.getDescripcionmascota());
        map.put("edadmascota",user.getEdadmascota());
        map.put("timestamp", new Date().getTime());
        map.put("imageCover", user.getImageCover());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updates(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("fon", user.getFon());
        map.put("timestamp", new Date().getTime());
        map.put("imageProfile", user.getImageProfile());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updatess(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("fon", user.getFon());
        map.put("timestamp", new Date().getTime());
        return mCollection.document(user.getId()).update(map);
    }
    public Task<Void> updatesss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("timestamp", new Date().getTime());
        map.put("imageProfile", user.getImageProfile());
        return mCollection.document(user.getId()).update(map);
    }
    public Task<Void> updatessss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("fon", user.getFon());
        map.put("timestamp", new Date().getTime());
        map.put("imageProfile", user.getImageProfile());
        return mCollection.document(user.getId()).update(map);
    }
    public Task<Void> updatesssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("timestamp", new Date().getTime());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updatessssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("fon", user.getFon());
        map.put("timestamp", new Date().getTime());
        return mCollection.document(user.getId()).update(map);
    }
    public Task<Void> updatesssssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date().getTime());
        map.put("imageProfile", user.getImageProfile());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updatessssssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("mascota", user.getMascota());
        map.put("descripcionmascota", user.getDescripcionmascota());
        map.put("edadmascota", user.getEdadmascota());
        map.put("timestamp", new Date().getTime());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updatesssssssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("mascota", user.getMascota());
        map.put("descripcionmascota", user.getDescripcionmascota());
        map.put("timestamp", new Date().getTime());
        map.put("imageCover", user.getImageCover());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updatessssssssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("mascota", user.getMascota());
        map.put("descripcionmascota", user.getDescripcionmascota());
        map.put("timestamp", new Date().getTime());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updatesssssssssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("descripcionmascota", user.getDescripcionmascota());
        map.put("edadmascota", user.getEdadmascota());
        map.put("timestamp", new Date().getTime());
        map.put("imageCover", user.getImageCover());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updatessssssssssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("descripcionmascota", user.getDescripcionmascota());
        map.put("timestamp", new Date().getTime());
        return mCollection.document(user.getId()).update(map);
    }

    public Task<Void> updatesssssssssssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("descripcionmascota", user.getDescripcionmascota());
        map.put("edadmascota", user.getEdadmascota());
        map.put("timestamp", new Date().getTime());
        return mCollection.document(user.getId()).update(map);
    }


    public Task<Void> updatessssssssssssss(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("descripcionmascota", user.getDescripcionmascota());
        map.put("timestamp", new Date().getTime());
        map.put("imageCover", user.getImageCover());
        return mCollection.document(user.getId()).update(map);
    }
    public Task<Void> updateOnline(String idUser, boolean status) {
        Map<String, Object> map = new HashMap<>();
        map.put("online", status);
        map.put("lastConnect", new Date().getTime());
        return mCollection.document(idUser).update(map);
    }

}