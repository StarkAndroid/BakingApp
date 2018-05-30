package com.example.serwis.bakingapp.Widget;

import android.app.IntentService;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.serwis.bakingapp.R;

/**
 * Created by serwis on 2018-05-30.
 */

public class RecentRecipeService extends IntentService {

    public RecentRecipeService (){
        super("RecentrecipeService");
    }

    public static void startActionUpdateRecipeWidgets(Context context){
        Intent intent = new Intent(context, RecentRecipeService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        updateRecipe();
    }

    public void updateRecipe(){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecentRecipeWidget.class));
        //Now update all widgets

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_needed_TV);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_name_TV);
        RecentRecipeWidget.updateRecipeWidget(this, appWidgetManager, appWidgetIds );
    }
}
