package id.web.androidhyper.bakingapp.ui.receivelist;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.web.androidhyper.bakingapp.R;

/**
 * Created by wildhan on 8/26/2017 in BakingApp.
 * Keep Spirit!!
 */

final class VHReceive extends RecyclerView.ViewHolder {
    @BindView(R.id.name_receive)
    TextView mTvReceive;
    @BindView(R.id.holder_receive)
    CardView mHolderReceive;
    @BindView(R.id.img_receive)
    ImageView mIvReceive;
    private static Unbinder unbinder;
    VHReceive(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this,itemView);
    }

    public static void unbindReceiveAdapter(){
        if(unbinder!=null)
            unbinder.unbind();
    }
}
