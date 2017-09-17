package id.web.androidhyper.bakingapp.ui.stepssegment.liststeps;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.web.androidhyper.bakingapp.AppUtils;
import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.model.MainModel;
import id.web.androidhyper.bakingapp.ui.stepssegment.StepsActivity;
import id.web.androidhyper.bakingapp.ui.stepssegment.detailstep.DetailStepsFragment;

import static id.web.androidhyper.bakingapp.ConstantUtils.CURRENT_ITEM_POSITION;
import static id.web.androidhyper.bakingapp.ConstantUtils.CURRENT_STATE_POSITION;
import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_POSITION_LIST;
import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_SELECTED_RECEIVE;
import static id.web.androidhyper.bakingapp.ConstantUtils.STEPS_DETAIL_FRAGMENT_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsListFragment extends Fragment implements StepsAdapter.StepsClickListener{


    public StepsListFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_steps)
    RecyclerView mRvSteps;

    Unbinder unbinder;
    int currentPosition =-1;
    MainModel mData = null;
    Bundle bundles =null;
    StepsAdapter mStepAdapter;
    LinearLayoutManager lnManager;
    int currentScrollPosition;
    DetailStepsFragment detailStepsFragment;
    boolean isLandscapeMode = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mData = bundle.getParcelable(KEY_SELECTED_RECEIVE);
            bundles = new Bundle();
            bundles.putParcelable(KEY_SELECTED_RECEIVE,
                    bundle.getParcelable(KEY_SELECTED_RECEIVE));
        }
        if(savedInstanceState!=null)
            currentPosition = savedInstanceState.getInt(CURRENT_ITEM_POSITION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_steps_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        initStepsList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_STATE_POSITION, lnManager.findFirstVisibleItemPosition());
        outState.putInt(CURRENT_ITEM_POSITION, currentPosition);
        if (detailStepsFragment != null) {
            try {
                getActivity().getSupportFragmentManager().putFragment(outState, STEPS_DETAIL_FRAGMENT_NAME, detailStepsFragment);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    void initStepsList(){
        mStepAdapter = new StepsAdapter(getContext(),mData,this);
        lnManager = new LinearLayoutManager(getContext());
        mRvSteps.setLayoutManager(lnManager);
        mRvSteps.setAdapter(mStepAdapter);

        if(currentScrollPosition!=0) {
            lnManager.scrollToPosition(currentScrollPosition);
            mRvSteps.smoothScrollToPosition(currentScrollPosition);
        }
        if(currentPosition!=-1) {
            mStepAdapter.setSelectedPosition(currentPosition);
            mStepAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }

    @Override
    public void onStepsClicked(int position) {
        currentPosition = position;
        detailStepsFragment = new DetailStepsFragment();
        bundles.putInt(KEY_POSITION_LIST,position);
        detailStepsFragment.setArguments(bundles);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(!AppUtils.isLargeScreen(getContext()))
            transaction.addToBackStack(null);
        if(!isLandscapeMode) {
            if(getActivity() instanceof StepsActivity){
                if(((StepsActivity)getActivity()).isTablet()){
                    transaction.replace(R.id.container_detail, detailStepsFragment);
                }else{
                    transaction.replace(R.id.container_steps, detailStepsFragment);
                }
            }
        }
        transaction.commit();
    }
}
