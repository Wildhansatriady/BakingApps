package id.web.androidhyper.bakingapp.ui.stepssegment.detailstep;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.web.androidhyper.bakingapp.AppUtils;
import id.web.androidhyper.bakingapp.R;
import id.web.androidhyper.bakingapp.model.MainModel;
import id.web.androidhyper.bakingapp.model.StepModel;
import id.web.androidhyper.bakingapp.ui.stepssegment.StepsActivity;
import timber.log.Timber;

import static id.web.androidhyper.bakingapp.ConstantUtils.CURRENT_PLAYBACK_POSITION;
import static id.web.androidhyper.bakingapp.ConstantUtils.CURRENT_WINDOW_POSITION;
import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_POSITION_LIST;
import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_SELECTED_RECEIVE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStepsFragment extends Fragment {


    public DetailStepsFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.player_view)
    SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.tv_contentdesc)
    TextView mContentDesc;
    @BindView(R.id.short_desc)
    TextView mShortDesc;
    @BindView(R.id.bt_next)
    Button btNextStep;
    @BindView(R.id.bt_prev)
    Button btPrevStep;
    Bundle mBundle;
    SimpleExoPlayer mPlayer;
    Unbinder unbinder;
    long playbackPosition;
    int currentWindow;
    boolean playWhenReady=true;
    int currentStepPos;
    MainModel mData;
    StepModel mStepData;
    String url = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            Timber.d("Callstate Playback Position "+playbackPosition+" Windows Position"+currentWindow);

            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_POSITION);
            playbackPosition = savedInstanceState.getLong(CURRENT_PLAYBACK_POSITION);
        }
        if(this.getArguments()!=null){
            mBundle = this.getArguments();
            currentStepPos = mBundle.getInt(KEY_POSITION_LIST);
            mData= mBundle.getParcelable(KEY_SELECTED_RECEIVE);
            mStepData = mData != null ? mData.getSteps().get(currentStepPos) : null;
            url = mData != null ? mData.getSteps().get(currentStepPos).getVideoURL() : null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_steps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        initView();
        if(((StepsActivity)getActivity()).isLandscape()) {
            if(AppUtils.isLargeScreen(getContext())){
                btNextStep.setVisibility(View.GONE);
                btPrevStep.setVisibility(View.GONE);
            }
        }
        if(AppUtils.isLargeScreen(getContext())){
            btNextStep.setVisibility(View.GONE);
            btPrevStep.setVisibility(View.GONE);
        }
    }

    void initView(){
        if (url==null||url.isEmpty())
            mSimpleExoPlayerView.setVisibility(View.GONE);
        else
            mSimpleExoPlayerView.setVisibility(View.VISIBLE);

        if(currentStepPos==mData.getSteps().size()-1){
            btNextStep.setVisibility(View.GONE);
            btPrevStep.setVisibility(View.VISIBLE);
        }else{
            if(currentStepPos!=0)
                btPrevStep.setVisibility(View.VISIBLE);
            String titleNext=getString(R.string.next_to_step);//+(currentStepPos+1);
            btNextStep.setText(titleNext);
        }

        if(currentStepPos==0){
            btPrevStep.setVisibility(View.GONE);
        }else{
            if(currentStepPos!=mData.getSteps().size()-1)
                btNextStep.setVisibility(View.VISIBLE);
            String titlePrev = getString(R.string.prev_to_step);//+(currentStepPos-1);
            btPrevStep.setText(titlePrev);
        }
        mShortDesc.setText(mStepData.getShortDescription());
        mContentDesc.setText(mStepData.getDescription());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        releasePlayer();

        Timber.d("Outstate Playback Position "+playbackPosition+" Windows Position"+currentWindow);
        if(playbackPosition!=0) {
            outState.putLong(CURRENT_PLAYBACK_POSITION, playbackPosition);
        }
        outState.putInt(CURRENT_WINDOW_POSITION,currentWindow);
    }

    @OnClick(R.id.bt_next)
    void onStepNext(){
        releasePlayer();
        playbackPosition=0;
        if(currentStepPos!=mData.getSteps().size()-1) {
            currentStepPos++;
            mStepData = mData != null ? mData.getSteps().get(currentStepPos) : null;
            url = mData != null ? mData.getSteps().get(currentStepPos).getVideoURL() : null;
            if(url!=null)
                initExoPlayer(url);
            initView();
        }
    }

    @OnClick(R.id.bt_prev)
    void onStepPrev(){
        releasePlayer();
        playbackPosition=0;
        if(currentStepPos!=0) {
            currentStepPos--;
            mStepData = mData != null ? mData.getSteps().get(currentStepPos) : null;
            url = mData != null ? mData.getSteps().get(currentStepPos).getVideoURL() : null;
            if(url!=null)
                initExoPlayer(url);
            initView();
        }
    }

    void initExoPlayer(String url){

        mPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());


        mPlayer.setPlayWhenReady(playWhenReady);
        mPlayer.seekTo(currentWindow,playbackPosition);
        mSimpleExoPlayerView.requestFocus();
        mSimpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        mSimpleExoPlayerView.setPlayer(mPlayer);

        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);
        mPlayer.prepare(mediaSource, true, false);
    }
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mSimpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23&&url!=null) {
            initExoPlayer(url);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getActivity() instanceof StepsActivity){
            if(((StepsActivity)getActivity()).isLandscape()) {
                if(!AppUtils.isLargeScreen(getContext())){
                    // on a large screen device ...
                    hideSystemUi();
                }
            }

        }
        if ((Util.SDK_INT <= 23 || mPlayer == null)&&url!=null) {
            initExoPlayer(url);
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    private void releasePlayer() {
        if (mPlayer != null) {
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            Timber.d("Outstate Playback Position "+playbackPosition+" Windows Position"+currentWindow);
            //playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }
    private void showSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(getActivity()!=null){
            if(((StepsActivity)getActivity()).isLandscape())
                if(!AppUtils.isLargeScreen(getContext()))
                    showSystemUI();
        }
        if(unbinder!=null)
            unbinder.unbind();
    }


}
