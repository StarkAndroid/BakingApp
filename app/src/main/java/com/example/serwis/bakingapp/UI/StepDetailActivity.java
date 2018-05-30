package com.example.serwis.bakingapp.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serwis.bakingapp.Network.BakingRepo;
import com.example.serwis.bakingapp.R;
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

import static com.example.serwis.bakingapp.UI.DetailActivity.STEP_ID;

public class StepDetailActivity extends AppCompatActivity {

    BakingRepo CurrentRecipe;
    int StepPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

         if (savedInstanceState!=null){
                CurrentRecipe = (BakingRepo) savedInstanceState.getSerializable(MainActivity.RECIPE_OBJECT);
                StepPosition = savedInstanceState.getInt(STEP_ID);
         }

         else {
            Intent getterIntent = getIntent();
            CurrentRecipe = (BakingRepo) getterIntent.getSerializableExtra(MainActivity.RECIPE_OBJECT);
            StepPosition = getterIntent.getIntExtra(STEP_ID, 0);

            StepDetailFragment stepDetailFragment = new StepDetailFragment();

            stepDetailFragment.setCurrentRecipe(CurrentRecipe);
            stepDetailFragment.setStepPosition(StepPosition);


            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_for_fragment, stepDetailFragment)
                    .commit();
         }

    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(MainActivity.RECIPE_OBJECT, CurrentRecipe);
        bundle.putInt(STEP_ID, StepPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent newIntent = new Intent(StepDetailActivity.this, DetailActivity.class);
                newIntent.putExtra(MainActivity.RECIPE_OBJECT, CurrentRecipe);
                newIntent.putExtra(STEP_ID, StepPosition);
                NavUtils.navigateUpTo(StepDetailActivity.this, newIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
