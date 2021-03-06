package com.example.mohammed.baking_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        Root root = recipes.get(i);

        String recipeName = root.getName();

        recipeViewHolder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(context, ReceipeListActivity.class);
            intent.putExtra(ReceipeListActivity.RECIPE_DATA, recipes.get(recipeViewHolder.getAdapterPosition()));
            context.startActivity(intent);
        });

        switch (root.getId()) {
            case 1:
                recipeViewHolder.recipeImage.setImageResource(R.drawable.nutella_pie);
                break;
            case 2:
                recipeViewHolder.recipeImage.setImageResource(R.drawable.brownies);
                break;
            case 3:
                recipeViewHolder.recipeImage.setImageResource(R.drawable.yellow_cake);
                break;
            case 4:
                recipeViewHolder.recipeImage.setImageResource(R.drawable.cheese_cake);
                break;
            default:
                recipeViewHolder.recipeImage.setImageResource(R.drawable.ic_launcher_background);

        }

        recipeViewHolder.recipeName.setText(recipeName);
    }

    @Override
    public int getItemCount() {

        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView recipeName;
        private ImageView recipeImage;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeImage = itemView.findViewById(R.id.recipe_image);
        }
    }

}
