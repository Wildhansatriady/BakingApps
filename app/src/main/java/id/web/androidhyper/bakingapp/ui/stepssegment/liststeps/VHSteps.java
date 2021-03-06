package id.web.androidhyper.bakingapp.ui.stepssegment.liststeps;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.web.androidhyper.bakingapp.R;

/**
 * Created by wildhan on 8/27/2017 in BakingApp.
 * Keep Spirit!!
 */

public class VHSteps extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_btplay)
    ImageView mBtPlay;
    @BindView(R.id.iv_liststeps)
    ImageView mIvListSteps;
    @BindView(R.id.parent_steps)
    ConstraintLayout mParentSteps;
    @BindView(R.id.tvStep)
    TextView mTvSteps;
    VHSteps(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
