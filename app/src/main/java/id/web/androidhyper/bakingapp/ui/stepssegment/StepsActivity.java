package id.web.androidhyper.bakingapp.ui.stepssegment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.web.androidhyper.bakingapp.AppUtils;
import id.web.androidhyper.bakingapp.ConstantUtils;
import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.model.MainModel;
import id.web.androidhyper.bakingapp.ui.stepssegment.detailstep.DetailStepsFragment;
import id.web.androidhyper.bakingapp.ui.stepssegment.liststeps.StepsListFragment;

import static id.web.androidhyper.bakingapp.ConstantUtils.STEPS_LIST_FRAGMENT_NAME;

public class StepsActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    MainModel mData;
    Unbinder unbinder;
    StepsListFragment stepsFragment;
    DetailStepsFragment detailStepsFragment;

    boolean isTablet =false;
    boolean canback=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        unbinder = ButterKnife.bind(this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if(findViewById(R.id.container_detail)!=null){
            isTablet = true;
        }
        Bundle bundle = getIntent().getExtras().getBundle(ConstantUtils.KEY_PASSMAINDATA);
        if (bundle != null) {
            mData = bundle.getParcelable(ConstantUtils.KEY_SELECTED_RECEIVE);
            if (mData != null) {
                AppUtils.setUpHomeButton(this,"Steps How to Make "+mData.getName());
                initFragmentSteps(savedInstanceState,bundle);
            }
        }

    }


    public boolean isTablet() {
        return isTablet;
    }

    public  boolean isLandscape(){
        return AppUtils.isDefaultLandscape(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, STEPS_LIST_FRAGMENT_NAME, stepsFragment);
        //getSupportFragmentManager().putFragment(outState, "detailStepsFragment", stepsFragment);

    }


    void initFragmentSteps(Bundle savedState,Bundle bundle){
        if(savedState!=null){
            if(getSupportFragmentManager().getFragment(savedState, STEPS_LIST_FRAGMENT_NAME) instanceof StepsListFragment) {
                stepsFragment
                        = (StepsListFragment) getSupportFragmentManager().getFragment(savedState, STEPS_LIST_FRAGMENT_NAME);
            }
        }else {
            stepsFragment = new StepsListFragment();
            stepsFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_steps, stepsFragment);
            transaction.commit();

            if(isTablet){
                detailStepsFragment = new DetailStepsFragment();

                // TODO: 9/13/2017
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(canback) {
            getSupportFragmentManager().popBackStack();
            return true;
        }else
            return AppUtils.backUpButtonAction(item, this);
        //return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackStackChanged() {
        canback = getSupportFragmentManager().getBackStackEntryCount()>0;

    }

}
