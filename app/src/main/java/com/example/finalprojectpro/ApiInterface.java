package com.example.finalprojectpro;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Content-type:"+Constants.CONTENT_TYPE,"Authorization: key="+Constants.SERVICE_KEY})
    @POST("fcm/send")
    Call<ResponseBody>SendNearByNotification(@Body RequestNotification requestNotification);
}
