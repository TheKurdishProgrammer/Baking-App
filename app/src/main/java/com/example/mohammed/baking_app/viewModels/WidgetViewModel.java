package com.example.mohammed.baking_app.viewModels;

import com.example.mohammed.baking_app.models.IngredientsBean;

import java.util.List;

public class WidgetViewModel {

    private String recipeName;
    private List<IngredientsBean> ingredients;

    public List<IngredientsBean> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsBean> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeName() {

        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
