package com.example.mohammed.baking_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.activitiesAndFragments.ReceipeListActivity;
import com.example.mohammed.baking_app.models.Root;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    private List<Root> recipes;
    private Context context;

    public RecipeAdapter(List<Root> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, viewGroup, false);


        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder recipeViewHolder, int i) {

        recipeViewHolder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, ReceipeListActivity.class);
            intent.putExtra(ReceipeListActivity.RECIPE_DATA, recipes.get(recipeViewHolder.getAdapterPosition()));
            context.startActivity(intent);
        });

        String recipeName = recipes.get(i).getName();
        recipeViewHolder.recipeName.setText(recipeName);
        Log.e("Img", recipes.get(i).getImage());
    }

    @Override
    public int getItemCount() {

        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView recipeName;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
        }
    }

}
