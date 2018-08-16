package com.example.mohammed.baking_app.appWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.mohammed.baking_app.R;
import com.example.mohammed.baking_app.activitiesAndFragments.MainActivity;
import com.example.mohammed.baking_app.utils.SharedPrefUtil;
import com.example.mohammed.baking_app.viewModels.WidgetViewModel;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    static void updateAppWidget(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients);


        WidgetViewModel widgetViewModel = SharedPrefUtil.getWidgetViewModel(context);
        views.setTextViewText(R.id.recipe_name_widget, widgetViewModel.getRecipeName());

        SharedPrefUtil.ingredientToJson(context, widgetViewModel.getIngredients());


        Intent intent = new Intent(context, RecipeRemoteViewFactory.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.ingredients_list_widget, intent);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.ingredients_list_widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, "Hello Widget", Toast.LENGTH_SHORT).show();
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

    }
}

