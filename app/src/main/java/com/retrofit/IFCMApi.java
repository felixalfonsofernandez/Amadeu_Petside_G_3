package com.retrofit;

import com.example.registrarse.FCMBody;
import com.example.registrarse.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMApi {

    @Headers({
            "Contend-Type:application/json",
            "Authorization:key=AAAAUTMtim4:APA91bG4ZDp5sAjGuE9DI9bP2hOjXl7NYWGGWXa9ZILYm9Xbg0jmquA9JlkGxznIguF8wsjbyOaPlLyqPdMdhgU4pKrfx6VT-p_mKhgCLI3Svz5DGjCkM6bcIigO4zaDUVDIhjowts5S"
    })
    @POST("fmc/send")
    Call<FCMResponse> send(@Body FCMBody body);


}
