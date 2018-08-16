package com.example.mohammed.baking_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.mohammed.baking_app.appWidget.RecipeRemoteViewFactory;
import com.example.mohammed.baking_app.models.IngredientsBean;
import com.example.mohammed.baking_app.models.Root;
import com.example.mohammed.baking_app.viewModels.WidgetViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static com.example.mohammed.baking_app.activitiesAndFragments.ReceipeListActivity.RECIPE_DATA;

public class SharedPrefUtil {


    private static final String RECIPE_WIDGET_POS = "RECIPE_WIDGET_POS";

    public static void ingredientToJson(Context context, List<IngredientsBean> ingredients) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        Type type = new TypeToken<List<IngredientsBean>>() {
        }.getType();

        Gson gson = new Gson();

        String jsonIngredients = gson.toJson(ingredients, type);

        editor.putString(RecipeRemoteViewFactory.INGRIEDNTS, jsonIngredients);

        editor.apply();


    }

    public static List<IngredientsBean> jsonToIngredient(Context context) {

        SharedPreferences preferences = getSharedPreferences(context);

        String jsonIngredients = preferences.getString(RecipeRemoteViewFactory.INGRIEDNTS, "");

        Type type = new TypeToken<List<IngredientsBean>>() {
        }.getType();

        Gson gson = new Gson();

        List<IngredientsBean> ingredientsBeans = gson.fromJson(jsonIngredients, type);


        return ingredientsBeans;
    }

    public static void saveAllDataToPreferences(Context context, String dataJson) {

        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(RECIPE_DATA, dataJson);

        editor.apply();


    }


    public static List<Root> getRecipesFromPreferences(Context context) {

        SharedPreferences preferences = getSharedPreferences(context);
        String dataJson = preferences.getString(RECIPE_DATA, null);

        if (dataJson == null)
            return null;

        Gson gson = new Gson();
        Type type = new TypeToken<List<Root>>() {
        }.getType();

        List<Root> recipes = gson.fromJson(dataJson, type);


        return recipes;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void storeRecipePositionWidget(Context context, int position) {

        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RECIPE_WIDGET_POS, position);

        editor.apply();


    }

    public static WidgetViewModel getWidgetViewModel(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        int selectedRecipeIndex = preferences.getInt(RECIPE_WIDGET_POS, 0);

        String recipeJsonData = preferences.getString(RECIPE_DATA, null);

        Type type = new TypeToken<List<Root>>() {
        }.getType();


        List<Root> recipes = new Gson().fromJson(recipeJsonData, type);

        WidgetViewModel viewModel = new WidgetViewModel();

        viewModel.setRecipeName(recipes.get(selectedRecipeIndex).getName());
        viewModel.setIngredients(recipes.get(selectedRecipeIndex).getIngredients());

        return viewModel;
    }
}
