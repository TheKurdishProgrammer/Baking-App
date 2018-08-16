package com.example.mohammed.baking_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.models.IngredientsBean;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {


    private ArrayList<IngredientsBean> ingredients;
    private Context context;

    public IngredientsAdapter(ArrayList<IngredientsBean> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, viewGroup, false);


        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientViewHolder recipeViewHolder, int i) {

        String ingredientName = ingredients.get(i).getIngredient();
        double quantity = ingredients.get(i).getQuantity();
        String measure = ingredients.get(i).getMeasure();

        recipeViewHolder.ingredientName.setText(context.getString(R.string.ingredient_quantity, quantity, measure, ingredientName));

    }

    @Override
    public int getItemCount() {

        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientName;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
        }
    }

}
