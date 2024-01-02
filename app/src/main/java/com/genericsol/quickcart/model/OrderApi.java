package com.genericsol.quickcart.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface OrderApi {

    @GET("api/v1/orders")
    Call<List<OrderModel>> getOrderList();

    @POST("api/v1/orders")
    Call<OrderModel> postOrder(@Body OrderModel orderModel);

}
