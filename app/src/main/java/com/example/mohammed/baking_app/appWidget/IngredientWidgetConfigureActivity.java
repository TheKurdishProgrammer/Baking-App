package com.example.mohammed.baking_app.appWidget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.activitiesAndFragments.MainActivity;
import com.example.mohammed.baking_app.databinding.RecipeIngredientChooseActivityBinding;
import com.example.mohammed.baking_app.models.Root;
import com.example.mohammed.baking_app.utils.SharedPrefUtil;

import java.util.List;

import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

public class IngredientWidgetConfigureActivity extends AppCompatActivity {


    private RecipeIngredientChooseActivityBinding binding;
    private int appWidgetId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);

        binding = DataBindingUtil.setContentView(this, R.layout.recipe_ingredient_choose_activity);

        appWidgetId = getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            appWidgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }


        if (appWidgetId == INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        List<Root> root = SharedPrefUtil.getRecipesFromPreferences(this);
        if (root == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        populateSpinner(root);

    }

    private void populateSpinner(List<Root> recipes) {

        String[] recipenames = new String[recipes.size()];

        for (int i = 0; i < recipenames.length; i++) {
            recipenames[i] = recipes.get(i).getName();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipenames);

        binding.spinner.setAdapter(adapter);

        binding.chooseRecipeIngredient.setOnClickListener(v -> {
            int selectedSpinnerIndex = binding.spinner.getSelectedItemPosition();

            SharedPrefUtil.storeRecipePositionWidget(this, selectedSpinnerIndex);

            AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());

            RecipeAppWidget.updateAppWidget(this, manager, appWidgetId);

            Intent intent = new Intent();
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, intent);
            finish();


        });

    }

}
