package com.genericsol.quickcart.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {

    @GET("/api/v1/categories")
    Call<List<CategoryModel>> getCategories();

}
