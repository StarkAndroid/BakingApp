package com.example.serwis.bakingapp.Widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.serwis.bakingapp.R;
import com.example.serwis.bakingapp.UI.DetailActivity;
import com.example.serwis.bakingapp.UI.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecentRecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recent_recipe_widget);

        SharedPreferences prefs = context.getSharedPreferences(DetailActivity.WidgetPrefs, context.MODE_PRIVATE);
        String restoredName = prefs.getString(DetailActivity.RecipeName, "Here you will see recipe name");
        String restoredText = prefs.getString(DetailActivity.RecipeIngredients, "Here you will see recipe ingredients");
        if (!restoredText.isEmpty()) {
            views.setTextViewText(R.id.widget_recipe_name_TV, restoredName);
            views.setTextViewText(R.id.widget_ingredients_needed_TV, restoredText);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.widget_ingredients_needed_TV, pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, int[]appWidgets){
        for(int appWidget: appWidgets){
            updateAppWidget(context, appWidgetManager, appWidget);
        }
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
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

