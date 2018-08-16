package com.example.mohammed.baking_app.activitiesAndFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.adapter.SimpleItemRecyclerViewAdapter;
import com.example.mohammed.baking_app.models.IngredientsBean;
import com.example.mohammed.baking_app.models.Root;
import com.example.mohammed.baking_app.models.StepsBean;

import java.util.ArrayList;

/**
 * An activity representing a list of Receipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReceipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ReceipeListActivity extends AppCompatActivity {

    public static final String RECIPE_DATA = "RECIPE_DATA";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private CardView ingredientCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_list);
        ingredientCard = findViewById(R.id.ingredient_card);

        if (findViewById(R.id.receipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        Root root = getIntent().getParcelableExtra(RECIPE_DATA);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(root.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<IngredientsBean> ingredients = (ArrayList<IngredientsBean>) root.getIngredients();

        TextView textView = findViewById(R.id.ingriendntsTv);

        textView.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecipeIngriendtsAcivitty.class);
            intent.putParcelableArrayListExtra(RecipeIngriendtsAcivitty.INGREDIENTS, ingredients);
            startActivity(intent);

        });


        ArrayList<StepsBean> steps = (ArrayList<StepsBean>) root.getSteps();


        RecyclerView recyclerView = findViewById(R.id.receipe_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SimpleItemRecyclerViewAdapter adapter = new SimpleItemRecyclerViewAdapter(this, steps, mTwoPane);
        recyclerView.setAdapter(adapter);
        if (mTwoPane)
            adapter.displayFragment(0);


    }

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
}
