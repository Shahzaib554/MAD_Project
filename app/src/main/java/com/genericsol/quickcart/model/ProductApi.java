package com.genericsol.quickcart.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ProductApi {
    @GET("api/v1/products")
    Call<List<ProductModel>> getProducts();

    @GET("api/v1/products/{categoryId}")
    Call<List<ProductModel>> getProductsByCategory(@Path("categoryId") String categoryId);
}
