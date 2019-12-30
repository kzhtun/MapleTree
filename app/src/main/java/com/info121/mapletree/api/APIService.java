package com.info121.mapletree.api;


import com.info121.mapletree.models.ObjectRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {


    @GET("validateuser/{username},{password},{secretkey}")
    Call<ObjectRes> ValidateUser(@Path("username") String username, @Path("password") String password, @Path("secretkey") String secretkey);

    @GET("getUserProfile/{username},{secretkey}")
    Call<ObjectRes> GetUserProfile(@Path("username") String username, @Path("secretkey") String secretkey);

    @GET("getRounds")
    Call<ObjectRes> GetRounds();


}
