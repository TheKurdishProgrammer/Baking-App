package com.example.mohammed.baking_app.NetworkUtils;

import com.example.mohammed.baking_app.models.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeData {

    @GET(ConstantLinks.recipeDataJson)
    Call<List<Root>> getReceipeData();
}
