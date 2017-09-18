package id.web.androidhyper.bakingapp.ui.stepssegment.liststeps;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.web.androidhyper.bakingapp.AppUtils;
import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.model.IngredientModel;
import id.web.androidhyper.bakingapp.model.MainModel;
import id.web.androidhyper.bakingapp.model.StepModel;

import static id.web.androidhyper.bakingapp.ConstantUtils.TYPE_HEADER;
import static id.web.androidhyper.bakingapp.ConstantUtils.TYPE_ITEM;

/**
 * Created by wildhan on 8/27/2017 in BakingApp.
 * Keep Spirit!!
 */

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MainModel mData;
    StepsClickListener listener;
    Context mContext;
    int selectedPosition=-1;
    boolean isLargeScreen = false;


    public StepsAdapter(Context context,MainModel mData, StepsClickListener listener) {
        this.mData = mData;
        this.listener = listener;
        this.mContext = context;
        isLargeScreen = AppUtils.isLargeScreen(mContext);
    }
    interface StepsClickListener{
        void onStepsClicked(int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_ingredients_layout, parent, false);
            return  new VHIngredients(v);
        }else if(viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_steps, parent, false);
            return new VHSteps(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof VHIngredients){
            VHIngredients vhIngredients = (VHIngredients) holder;
            byte index=1;
            vhIngredients.lnContainerIngredients.removeAllViews();
            for (IngredientModel data:mData.getIngredients()) {
                String ingredientHolder
                        = index+".("+data.getQuantity()+" "+data.getMeasure()+") "+data.getIngredient();
                vhIngredients.lnContainerIngredients.addView(addItem(ingredientHolder));
                index++;
            }
        }else {
            final VHSteps vhSteps = (VHSteps) holder;
            if(isLargeScreen) {
                if (selectedPosition == position - 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        vhSteps.mParentSteps.setBackgroundColor(mContext.getColor(R.color.primary_light));
                    } else {
                        vhSteps.mParentSteps.setBackgroundColor(mContext
                                .getResources()
                                .getColor(R.color.primary_light));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        vhSteps.mParentSteps.setBackgroundColor(mContext.getColor(R.color.putih));
                    } else {
                        vhSteps.mParentSteps.setBackgroundColor(mContext
                                .getResources()
                                .getColor(R.color.putih));
                    }
                }
            }
            StepModel data = mData.getSteps().get(position-1);
            AppUtils.setImage(mContext,data.getThumbnailURL(),vhSteps.mIvListSteps);
            vhSteps.mTvSteps.setText(data.getShortDescription());
            if(data.getVideoURL().equals("")){
                vhSteps.mBtPlay.setVisibility(View.GONE);
            }
            vhSteps.mParentSteps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AppUtils.isLargeScreen(mContext))
                        selectedPosition=position-1;
                    listener.onStepsClicked(vhSteps.getAdapterPosition()-1);
                    notifyDataSetChanged();

                }
            });
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    private TextView addItem(String title){
        TextView textView = new TextView(mContext);
        textView.setTextSize(16);
        textView.setPadding(0,10,10,10);
        textView.setText(title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(mContext.getColor(R.color.putih));
        }else {
            textView.setTextColor(mContext.getResources().getColor(R.color.putih));

        }
        return textView;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return mData.getSteps().size()+1;
    }
}
