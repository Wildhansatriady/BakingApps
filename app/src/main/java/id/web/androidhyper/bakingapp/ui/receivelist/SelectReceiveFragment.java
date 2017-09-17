package id.web.androidhyper.bakingapp.ui.receivelist;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.web.androidhyper.bakingapp.AppUtils;
import id.web.androidhyper.bakingapp.ConstantUtils;
import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.model.MainModel;
import id.web.androidhyper.bakingapp.ui.stepssegment.StepsActivity;
import timber.log.Timber;

import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_MAINDATA;
import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_PASSMAINDATA;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectReceiveFragment extends Fragment implements ReceiveAdapter.onClickReceive {


    public SelectReceiveFragment() {
        // Required empty public constructor
    }

    List<MainModel> mData = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mData = bundle.getParcelableArrayList(KEY_MAINDATA);
        }
    }

    @BindView(R.id.rvReceive)
    RecyclerView mRvReceive;
    ReceiveAdapter mReceiveAdapter;
    Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_receive, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        Timber.d(mData.get(0).getName());
        initListReceive(mData);
    }

    void initListReceive(List<MainModel> data){
        mReceiveAdapter = new ReceiveAdapter(this,data);
        if(!AppUtils.isLargeScreen(getContext())) {
            LinearLayoutManager lnManager = new LinearLayoutManager(getContext());
            mRvReceive.setLayoutManager(lnManager);
        }else{
            GridLayoutManager lnManager = new GridLayoutManager(getContext(),2);
            mRvReceive.setLayoutManager(lnManager);
        }
        mRvReceive.setHasFixedSize(true);
        mRvReceive.setAdapter(mReceiveAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null)
            unbinder.unbind();
    }

    @Override
    public void onReceiveClicked(int position) {
        Intent intent = new Intent(getContext(), StepsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtils.KEY_SELECTED_RECEIVE,mData.get(position));
        intent.putExtra(KEY_PASSMAINDATA,bundle);
        startActivity(intent);
    }
}
