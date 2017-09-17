package id.web.androidhyper.bakingapp.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wildhan on 8/26/2017 in BakingApp.
 * Keep Spirit!!
 */

public class ApiBuilder {
    private static final String HOST = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static Retrofit retrofit = null;
    public static Retrofit client() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if (retrofit==null) {
             retrofit = new Retrofit.Builder()
                        .baseUrl(HOST)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
        }
        return retrofit;
    }

}
