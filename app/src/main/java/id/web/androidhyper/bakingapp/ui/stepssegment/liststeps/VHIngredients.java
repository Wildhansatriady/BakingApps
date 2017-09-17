package id.web.androidhyper.bakingapp.ui.stepssegment.liststeps;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.web.androidhyper.bakingapp.R;

/**
 * Created by wildhan on 8/27/2017 in BakingApp.
 * Keep Spirit!!
 */

public class VHIngredients extends ViewHolder{
    @BindView(R.id.container_ingredients)
    LinearLayout lnContainerIngredients;
    static Unbinder unbinder;
    public VHIngredients(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this,itemView);
    }
}
