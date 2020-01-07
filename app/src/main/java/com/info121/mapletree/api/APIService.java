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

    @GET("getLevels")
    Call<ObjectRes> GetLevels();

    @GET("getUnits/{level},{code}")
    Call<ObjectRes> GetUnits(@Path("level") String level, @Path("code") String code);


    //        1. roundcode
//        2. block
//        3. level
//        4. unit
//        5. tennantcode
//        6. tennantname
//        7. status

    @GET("saveUnit/{roundCode},{block},{level},{unit},{tenantcode},{tenantname},{status}")
    Call<ObjectRes> SaveUnits(@Path("roundCode") String roundCode,
                              @Path("block") String block,
                              @Path("level") String level,
                              @Path("unit") String unit,
                              @Path("tenantcode") String tenantcode,
                              @Path("tenantname") String tenantname,
                              @Path("status") String status);



}
