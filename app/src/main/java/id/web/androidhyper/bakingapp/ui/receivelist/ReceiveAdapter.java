package id.web.androidhyper.bakingapp.ui.receivelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.web.androidhyper.bakingapp.AppUtils;
import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.model.MainModel;

/**
 * Created by wildhan on 8/26/2017 in BakingApp.
 * Keep Spirit!!
 */

public class ReceiveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MainModel> mData;
    private onClickReceive callback;
    private Context mContext;

    public ReceiveAdapter(onClickReceive callback, List<MainModel> mData) {
        this.mData = mData;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive, parent, false);
        return  new VHReceive(view);
//        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHReceive){
            VHReceive holderReceive = (VHReceive) holder;
            MainModel data =  mData.get(position);
            AppUtils.setImage(mContext,data.getImage(),holderReceive.mIvReceive);
            holderReceive.mTvReceive.setText(data.getName());
            holderReceive.mHolderReceive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onReceiveClicked(holder.getAdapterPosition());
                }
            });
        }
    }
    public interface onClickReceive{
        void onReceiveClicked(int position);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        VHReceive.unbindReceiveAdapter();
    }
}
