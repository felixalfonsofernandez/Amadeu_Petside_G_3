package com.providers;

import com.example.registrarse.FCMBody;
import com.example.registrarse.FCMResponse;
import com.retrofit.IFCMApi;
import com.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Retrofit;

public class NotificationProvider {

    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {

    }

    public Call<FCMResponse> sendNotification(FCMBody body) {
        return RetrofitClient.getClient(url).create(IFCMApi.class).send(body);
    }
}
