package com.genericsol.quickcart.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryApi {

    @GET("/api/v1/categories")
    Call<List<CategoryModel>> getCategories();


    @POST("/api/v1/categories")
    Call<CategoryModel> createCategory(@Body CategoryModel categoryModel);

    @PUT("/api/v1/categories/{categoryId}")
    Call<CategoryModel> updateCategory(
            @Path("categoryId") String categoryId,
            @Body CategoryModel updatedCategory
    );

}
