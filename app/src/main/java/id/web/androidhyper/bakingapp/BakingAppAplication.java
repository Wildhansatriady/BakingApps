package id.web.androidhyper.bakingapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by wildhan on 8/26/2017 in BakingApp.
 * Keep Spirit!!
 */

public class BakingAppAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree(){
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + " ( line :"+element.getLineNumber()+" ) ";
                }
            });
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
