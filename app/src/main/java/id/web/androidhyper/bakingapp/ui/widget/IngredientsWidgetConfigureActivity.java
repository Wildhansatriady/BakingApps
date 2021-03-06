package id.web.androidhyper.bakingapp.ui.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.model.MainModel;
import id.web.androidhyper.bakingapp.ui.receivelist.ReceiveAdapter;
import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * The configuration screen for the {@link IngredientsWidget IngredientsWidget} AppWidget.
 */
public class IngredientsWidgetConfigureActivity extends Activity implements ReceiveAdapter.onClickReceive {

    private static final String PREFS_NAME = "id.web.androidhyper.bakingapp.ui.widget.IngredientsWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    @BindView(R.id.rv_widget_conf)
    RecyclerView mRvWidgetConf;
    List<MainModel> datas;


    public IngredientsWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void setIdReceive(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.ingredients_widget_configure);
        ButterKnife.bind(this);
        //mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
        //findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<MainModel> query = realm.where(MainModel.class);
                datas = realm.copyFromRealm(query.findAll());
        ReceiveAdapter adapter = new ReceiveAdapter(this,datas);
        LinearLayoutManager mLayoutMngr = new LinearLayoutManager(getBaseContext());
        mRvWidgetConf.setLayoutManager(mLayoutMngr);
        mRvWidgetConf.setHasFixedSize(true);
        mRvWidgetConf.setAdapter(adapter);
        //mAppWidgetText.setText(loadTitlePref(IngredientsWidgetConfigureActivity.this, mAppWidgetId));
    }



    @Override
    public void onReceiveClicked(int position) {
        final Context context = IngredientsWidgetConfigureActivity.this;

        // When the button is clicked, store the string locally
        String idReceive = position+"";//mAppWidgetText.getText().toString();
        setIdReceive(context, mAppWidgetId, idReceive);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        IngredientsWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}

