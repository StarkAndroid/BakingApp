package com.example.serwis.bakingapp.UI;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serwis.bakingapp.Network.BakingRepo;
import com.example.serwis.bakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import static com.example.serwis.bakingapp.UI.DetailActivity.STEP_ID;

/**
 * Created by serwis on 2018-05-27.
 */

public class StepDetailFragment extends Fragment {

    SimpleExoPlayer player;
    com.google.android.exoplayer2.ui.PlayerView PlayerView;
    private long CurrentPosition;
    private int CurrentWindowIndex;
    private static final DefaultBandwidthMeter DEFAULT_BANDWIDTH_METER = new DefaultBandwidthMeter();
    BakingRepo CurrentRecipe;
    int StepPosition;
    Button PrevoiusButton;
    Button NextButton;
    TextView DetailInstructions;
    LinearLayout VideoFrameLayout;
    public String VIDEO_POSITION;
    ImageView RecipeImage;
    Boolean playState;
    String playerState = "player state";

    public StepDetailFragment(){
    }

    public void setCurrentRecipe(BakingRepo CurrentRecipe){
        this.CurrentRecipe = CurrentRecipe;
    }

    public void setStepPosition (int StepPosition){
        this.StepPosition = StepPosition;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        PlayerView = rootView.findViewById(R.id.BakingVideoView);
        VideoFrameLayout = rootView.findViewById(R.id.VideoFrameLayout);
        RecipeImage = rootView.findViewById(R.id.BakingImageView);



        if (savedInstanceState!=null){
            CurrentRecipe = (BakingRepo) savedInstanceState.getSerializable(MainActivity.RECIPE_OBJECT);
            StepPosition = savedInstanceState.getInt(STEP_ID);
            CurrentPosition = savedInstanceState.getLong(VIDEO_POSITION);
            playState = savedInstanceState.getBoolean(playerState);
        } else playState = false;

        if (CurrentRecipe!=null){

            PrevoiusButton = rootView.findViewById(R.id.Previous_Step_Button);
            NextButton = rootView.findViewById(R.id.Next_Step_Button);
            DetailInstructions = rootView.findViewById(R.id.Detail_Instructions_TextView);
            DetailInstructions.setText(CurrentRecipe.getSteps().get(StepPosition).getDescription());
            final String ImageUri = CurrentRecipe.getSteps().get(StepPosition).getThumbnailURL();


            NextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StepPosition = StepPosition + 1;
                    DetailInstructions.setText(CurrentRecipe.getSteps().get(StepPosition).getDescription());
                    changePlayerResource(CurrentRecipe.getSteps().get(StepPosition).getVideoURL(), ImageUri);
                }
            });

            PrevoiusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StepPosition = StepPosition - 1;
                    DetailInstructions.setText(CurrentRecipe.getSteps().get(StepPosition).getDescription());
                    changePlayerResource(CurrentRecipe.getSteps().get(StepPosition).getVideoURL(), ImageUri);
                }
            });
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(CurrentRecipe.getName());
            hideButtons(getContext());
            if (StepPosition==0) PrevoiusButton.setVisibility(View.INVISIBLE);
            else  PrevoiusButton.setVisibility(View.VISIBLE);
            if (StepPosition==CurrentRecipe.getSteps().size()-1) NextButton.setVisibility(View.INVISIBLE);
            else NextButton.setVisibility(View.VISIBLE);
            checkScreenOrientation(getContext());
        }
        return rootView;
    }
    private void initializePlayer(String VideoUri, String ImageUri){
        if (VideoUri.isEmpty()){
            PlayerView.setVisibility(View.GONE);
            RecipeImage.setVisibility(View.VISIBLE);
            if (ImageUri.isEmpty()) ImageUri = "error Uri";
            Picasso.with(getContext())
                    .load(ImageUri)
                    .placeholder(R.drawable.cupcake)
                    .error(R.drawable.cupcake)
                    .into(RecipeImage);
        } else {

            PlayerView.setVisibility(View.VISIBLE);
            RecipeImage.setVisibility(View.GONE);
        }

            if (player == null) {
                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                        new DefaultTrackSelector(), new DefaultLoadControl());

                PlayerView.setPlayer(player);

                player.setPlayWhenReady(playState);
                player.seekTo(CurrentWindowIndex, CurrentPosition);
            }

            Uri uri = Uri.parse(VideoUri);
            try {
                MediaSource mediaSource = buildMediaSource(uri);
                player.prepare(mediaSource, false, false);
            } catch (MalformedURLException e){
                Log.d("Tag", "MalformedURL");
            }
    }
    public void changePlayerResource(String VideoUri, String ImageUri){
        if (VideoUri.isEmpty()){
            PlayerView.setVisibility(View.GONE);
            RecipeImage.setVisibility(View.VISIBLE);
            if (ImageUri.isEmpty()) ImageUri = "error Uri";
            Picasso.with(getContext())
                    .load(ImageUri)
                    .placeholder(R.drawable.cupcake)
                    .error(R.drawable.cupcake)
                    .into(RecipeImage);
        } else {
            PlayerView.setVisibility(View.VISIBLE);
            RecipeImage.setVisibility(View.GONE);
            Uri uri = Uri.parse(VideoUri);
            MediaSource mediaSource = null;
            try {
                mediaSource = buildMediaSource(uri);
                player.prepare(mediaSource, false, false);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if (StepPosition==0) PrevoiusButton.setVisibility(View.INVISIBLE);
        else  PrevoiusButton.setVisibility(View.VISIBLE);
        if (StepPosition==CurrentRecipe.getSteps().size()-1) NextButton.setVisibility(View.INVISIBLE);
        else NextButton.setVisibility(View.VISIBLE);
    }
    private MediaSource buildMediaSource(Uri uri) throws MalformedURLException {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("m")).
                createMediaSource(uri);
    }

    private void releasePlayer(){
        if (player!=null){
            CurrentPosition = player.getCurrentPosition();
            CurrentWindowIndex = player.getCurrentWindowIndex();
            playState = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    public void checkScreenOrientation (Context context){
        int ActivityOrientation = StepDetailFragment.this.getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth < 720 && !CurrentRecipe.getSteps().get(StepPosition).getVideoURL().isEmpty()) {
            if (ActivityOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                LinearLayout.LayoutParams Landscape_Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                VideoFrameLayout.setLayoutParams(Landscape_Params);

                View decorView = getActivity().getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            String VideoUri = CurrentRecipe.getSteps().get(StepPosition).getVideoURL();
            String ImageUri = CurrentRecipe.getSteps().get(StepPosition).getThumbnailURL();
            initializePlayer(VideoUri, ImageUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            String VideoUri = CurrentRecipe.getSteps().get(StepPosition).getVideoURL();
            String ImageUri = CurrentRecipe.getSteps().get(StepPosition).getThumbnailURL();
            initializePlayer(VideoUri, ImageUri);
        }
    }



    public void hideButtons (Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth > 720){
            NextButton.setVisibility(View.GONE);
            PrevoiusButton.setVisibility(View.GONE);
        }
        else {
            NextButton.setVisibility(View.VISIBLE);
            PrevoiusButton.setVisibility(View.VISIBLE);
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

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(MainActivity.RECIPE_OBJECT, CurrentRecipe);
        bundle.putInt(STEP_ID, StepPosition);
        bundle.putLong(VIDEO_POSITION, CurrentPosition);
        bundle.putBoolean(playerState, playState);
    }
}
