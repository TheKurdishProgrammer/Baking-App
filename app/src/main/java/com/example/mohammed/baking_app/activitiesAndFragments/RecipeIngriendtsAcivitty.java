package com.example.mohammed.baking_app.activitiesAndFragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.adapter.IngredientsAdapter;
import com.example.mohammed.baking_app.models.IngredientsBean;

import java.util.ArrayList;

public class RecipeIngriendtsAcivitty extends AppCompatActivity {

    public static final String INGREDIENTS = "INGREDIENTS";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingriendts_acivitty);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<IngredientsBean> ingredients = getIntent().getParcelableArrayListExtra(INGREDIENTS);

        RecyclerView ingredientsList = findViewById(R.id.ingredients_list);
        ingredientsList.setHasFixedSize(true);
        ingredientsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients, this);
        ingredientsList.setAdapter(ingredientsAdapter);


    }

}
