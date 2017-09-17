package id.web.androidhyper.bakingapp.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.model.IngredientModel;
import id.web.androidhyper.bakingapp.model.MainModel;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link IngredientsWidgetConfigureActivity IngredientsWidgetConfigureActivity}
 */
public class IngredientsWidget extends AppWidgetProvider {



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String receivePos= IngredientsWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        Intent intent = new Intent(context, IngredientsWidgetConfigureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<MainModel> query = realm.where(MainModel.class);
        RealmResults<MainModel> datas = query.findAll();
        Timber.d("testsetes"+datas.size());
        views.setOnClickPendingIntent(R.id.chage_ingredients,configPendingIntent);
        String ingredients = context.getString(R.string.ingredients)+datas.get(Integer.parseInt(receivePos)).getName();
        views.setTextViewText(R.id.ingredients,ingredients);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        // Instruct the widget manager to update the widget

        byte index=1;
        String ingredientHolder = "";
        for (IngredientModel data:datas.get(Integer.parseInt(receivePos)).getRealmIngredients()) {

            ingredientHolder  += index+".("+data.getQuantity()+" "+data.getMeasure()+") "+data.getIngredient()+"\n";
            index++;
        }

        views.setTextViewText(R.id.container_ingredients,ingredientHolder);


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
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            IngredientsWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
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

