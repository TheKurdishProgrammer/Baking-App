package com.example.mohammed.baking_app.activitiesAndFragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.mohammed.baking_app.NetworkUtils.ReceipeDataService;
import com.example.mohammed.baking_app.NetworkUtils.RecipeData;
import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.adapter.RecipeAdapter;
import com.example.mohammed.baking_app.databinding.ActivityMainBinding;
import com.example.mohammed.baking_app.models.Root;
import com.example.mohammed.baking_app.utils.SharedPrefUtil;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String IDLING_RES_ID = "RECIPE_LOADER";
    private ActivityMainBinding binding;
    private CountingIdlingResource idlingResource = new CountingIdlingResource(IDLING_RES_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        RecipeData data = ReceipeDataService.getDefaultInstance().create(RecipeData.class);
        idlingResource.increment();

        getRecipes(data);

        binding.reload.setOnClickListener(v -> {
            binding.progress.setVisibility(View.VISIBLE);
            getRecipes(data);
        });
    }

    void getRecipes(RecipeData data) {
        data.getReceipeData().enqueue(new RecipeListCallback());
    }

    @VisibleForTesting
    public CountingIdlingResource getIdlingResources() {
        return idlingResource;
    }

    private class RecipeListCallback implements Callback<List<Root>> {
        @Override
        public void onResponse(@NonNull Call<List<Root>> call, @NonNull Response<List<Root>> response) {

            if (response.body() != null) {
                idlingResource.decrement();
                SharedPrefUtil.saveAllDataToPreferences(MainActivity.this, new Gson().toJson(response.body()));

                binding.recipeRecyler.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                binding.recipeRecyler.setHasFixedSize(true);
                RecipeAdapter adapter = new RecipeAdapter(response.body(), MainActivity.this);
                binding.recipeRecyler.setAdapter(adapter);

            }

            binding.noCoonn.setVisibility(View.GONE);
            binding.reload.setVisibility(View.GONE);
            binding.progress.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(@NonNull Call<List<Root>> call, @NonNull Throwable t) {
            Log.e("Error", t.getMessage());
            binding.noCoonn.setVisibility(View.VISIBLE);
            binding.reload.setVisibility(View.VISIBLE);
            binding.progress.setVisibility(View.GONE);
        }
    }
}
