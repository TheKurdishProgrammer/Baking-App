package com.example.mohammed.baking_app;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;

import com.example.mohammed.baking_app.activitiesAndFragments.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(JUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private CountingIdlingResource countingIdlingResource;

    @Before
    public void registerIdlingResources() {
        countingIdlingResource = activityTestRule.getActivity().getIdlingResources();
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    @After
    public void unRegisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }

    @Test
    public void testRecipeName() {

        // here you give the position of the recipe and its name to check if they are matching

        int recipePosition = 2;
        String recipeNameToBeTestedWith = "Yellow Cake";

        Espresso.onView(new RecyclerViewMatcher(R.id.recipe_recyler).atPositionOnView(recipePosition, R.id.recipe_name))
                .check(ViewAssertions.matches(withText(recipeNameToBeTestedWith)));

    }
}
