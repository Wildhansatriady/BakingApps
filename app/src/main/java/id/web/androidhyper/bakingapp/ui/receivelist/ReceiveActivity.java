package id.web.androidhyper.bakingapp.ui.receivelist;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.helper.SimpleIdlingResource;
import id.web.androidhyper.bakingapp.model.IngredientModel;
import id.web.androidhyper.bakingapp.model.MainModel;
import id.web.androidhyper.bakingapp.helper.APIInterface;
import id.web.androidhyper.bakingapp.helper.ApiBuilder;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_MAINDATA;

public class ReceiveActivity extends AppCompatActivity {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.layout_error)
    LinearLayout lnError;
    Unbinder unbinder;
    Realm realm;
    SimpleIdlingResource simpleIdlingResource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        getDataFromServer();

    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if (simpleIdlingResource == null) {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;
    }

    void getDataFromServer(){
        if(simpleIdlingResource!=null)
            simpleIdlingResource.setIdleState(false);

        lnError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        findViewById(R.id.main_container).setVisibility(View.GONE);
        APIInterface api = ApiBuilder.client().create(APIInterface.class);
        Call<List<MainModel>> call = api.getDataApp();
        call.enqueue(new Callback<List<MainModel>>() {
            @Override
            public void onResponse(Call<List<MainModel>> call, Response<List<MainModel>> response) {

                for (int i = 0; i <response.body().size() ; i++) {
                    //RealmList<IngredientModel> list = new RealmList();
                    response.body().get(i).setRealmIngredients(new RealmList<IngredientModel>());
                    RealmList<IngredientModel> list = response.body().get(i).getRealmIngredients();
                    for (int j = 0; j < response.body().get(i).getIngredients().size(); j++) {
                        list.add(response.body().get(i).getIngredients().get(j));
                    }

                    response.body().get(i).setRealmIngredients(list);
                }
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(response.body());
                realm.commitTransaction();
                findViewById(R.id.main_container).setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                lnError.setVisibility(View.GONE);

                initFragmentList(response.body());
                if(simpleIdlingResource!=null)
                    simpleIdlingResource.setIdleState(true);
            }

            @Override
            public void onFailure(Call<List<MainModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                lnError.setVisibility(View.VISIBLE);
                t.printStackTrace();
                findViewById(R.id.main_container).setVisibility(View.GONE);
            }
        });
    }

    private void initFragmentList(List<MainModel> data){
        SelectReceiveFragment receiveFragment = new SelectReceiveFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_MAINDATA, (ArrayList<? extends Parcelable>) data);
        receiveFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, receiveFragment);
        transaction.commit();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null)
            unbinder.unbind();
    }
}
