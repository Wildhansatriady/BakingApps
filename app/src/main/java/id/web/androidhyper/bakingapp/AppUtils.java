package id.web.androidhyper.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by wildhan on 8/26/2017 in BakingApp.
 * Keep Spirit!!
 */

public final class AppUtils {
    private static void hideKeyboard(AppCompatActivity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public static boolean isLargeScreen(Context context){
        // on a large screen device ...
        return ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE) || ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE);
    }
    public static boolean isDefaultLandscape(final Context context)
    {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        int orientation = context.getResources().getConfiguration().orientation;

        switch (rotation)
        {
            case Surface.ROTATION_180:
            case Surface.ROTATION_0:
            {
                return false;
            }
            default:
            {
                return true;
            }
        }
    }
    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static boolean backUpButtonAction(MenuItem item, Fragment fragment){
        if(item.getItemId()==android.R.id.home){
            hideKeyboard((AppCompatActivity) fragment.getActivity());
            fragment.getFragmentManager().popBackStack();
            return true;
        }else {
            return false;
        }
    }
    public static boolean backUpButtonAction(MenuItem item,AppCompatActivity activity){
        if(item.getItemId()==android.R.id.home){
            hideKeyboard(activity);
            activity.finish();
            return true;
        }else {
            return false;
        }
    }

    public static void setUpHomeButton(Fragment fragment,String title){
        AppCompatActivity activity1 = (AppCompatActivity)fragment.getActivity();
        ActionBar actionBar = null;
        if(activity1!=null)
            actionBar = activity1.getSupportActionBar();

        if(actionBar!=null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static void setUpHomeButton(AppCompatActivity activity,String title){
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }

    public static void setImage(Context mContext, String url, ImageView thumbImage){
        if(url!=null&&!url.equals("")&&!url.equals("-")) {
            Picasso.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.ic_cake_accent_48dp)
                    .error(R.drawable.ic_cake_accent_48dp)
                    .into(thumbImage);
        }
        else
            Picasso.with(mContext).load(R.drawable.ic_cake_accent_48dp)
                    .placeholder(R.drawable.ic_cake_accent_48dp)
                    .error(R.drawable.ic_cake_accent_48dp)
                    .into(thumbImage);

    }
}
