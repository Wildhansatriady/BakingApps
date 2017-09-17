package id.web.androidhyper.bakingapp.helper;

import java.util.List;

import id.web.androidhyper.bakingapp.model.MainModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by wildhan on 8/26/2017 in BakingApp.
 * Keep Spirit!!
 */

public interface APIInterface {
    @GET("baking.json")
    Call<List<MainModel>> getDataApp();
}
