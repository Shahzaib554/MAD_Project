package com.genericsol.quickcart.model;
import com.genericsol.quickcart.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/api/v1/users")
    Call<UserModel> createUser(@Body UserModel user);

    @GET("/api/v1/users")
    Call<List<UserModel>> getUsers();

}
