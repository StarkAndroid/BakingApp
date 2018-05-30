package com.example.serwis.bakingapp.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.serwis.bakingapp.Adapters.StepsAdapter;
import com.example.serwis.bakingapp.Network.BakingRepo;
import com.example.serwis.bakingapp.Network.steps;
import com.example.serwis.bakingapp.R;
import com.example.serwis.bakingapp.Widget.RecentRecipeService;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    BakingRepo CurrentRecipe;
    TextView IngredientsTextView;
    List<steps> steps;
    RecyclerView StepsRecyclerView;
    StepsAdapter stepsAdapter;
    public static final String STEP_ID = "Step ID";
    boolean mTwoPane = false;
    LinearLayoutManager linearLayoutManager;
    Parcelable LinearLayoutSaved;
    String LINEAR_LAYOUT_POSITION;
    public static final String WidgetPrefs = "Widget prefs";
    public static final String RecipeName = "RecipeName";
    public static final String RecipeIngredients = "Ingredients";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_list_fragment);

        if (findViewById(R.id.tablet_linear_layout)!=null)
            mTwoPane=true;

        StepsRecyclerView = findViewById(R.id.Steps_Adapter_View);
        IngredientsTextView = findViewById(R.id.Ingredients_Needed_Text_View);
        linearLayoutManager = new LinearLayoutManager(DetailActivity.this);
        if (LinearLayoutSaved!=null) linearLayoutManager.onRestoreInstanceState(LinearLayoutSaved);

        if (getIntent()!=null){
            Intent getterIntent = getIntent();
            CurrentRecipe = (BakingRepo) getterIntent.getSerializableExtra(MainActivity.RECIPE_OBJECT);
        }



        IngredientsTextView.setText(neededIngredients(CurrentRecipe));
        getSupportActionBar().setTitle(CurrentRecipe.getName());
        steps = CurrentRecipe.getSteps();
        stepsAdapter = new StepsAdapter(DetailActivity.this, steps);

        SharedPreferences.Editor editor = getSharedPreferences(WidgetPrefs, MODE_PRIVATE).edit();
        editor.putString(RecipeName, CurrentRecipe.getName());
        editor.putString(RecipeIngredients, neededIngredients(CurrentRecipe));
        editor.commit();
        RecentRecipeService.startActionUpdateRecipeWidgets(this);

        if (mTwoPane){
            initializeStepFragment(CurrentRecipe, 0);
            stepsAdapter.setmOnItemClickListener(new StepsAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View itemView, int position) {
                    initializeStepFragment(CurrentRecipe, position);
                }
            });
            StepsRecyclerView.setAdapter(stepsAdapter);
            StepsRecyclerView.setLayoutManager(linearLayoutManager);

        } else {
            stepsAdapter.setmOnItemClickListener(new StepsAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View itemView, int position) {
                    Intent newIntent = new Intent(DetailActivity.this, StepDetailActivity.class);
                    newIntent.putExtra(MainActivity.RECIPE_OBJECT, CurrentRecipe);
                    newIntent.putExtra(STEP_ID, position);
                    startActivity(newIntent);
                }
            });
            StepsRecyclerView.setAdapter(stepsAdapter);
            StepsRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    public void initializeStepFragment(BakingRepo CurrentRecipe, int position){
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setCurrentRecipe(CurrentRecipe);
        stepDetailFragment.setStepPosition(position);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getFragments()==null)
        fragmentManager.beginTransaction()
                .add(R.id.frame_for_fragment, stepDetailFragment)
                .commit();
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_for_fragment, stepDetailFragment)
                    .commit();
        }
    }

    public String neededIngredients(BakingRepo CurrentRecipe){
        String neededIngredients = "";
        int nrIngredients = CurrentRecipe.getIngredients().size();

        for (int a=0; a<nrIngredients; a++){
            neededIngredients = neededIngredients + CurrentRecipe
                    .getIngredients()
                    .get(a)
                    .getIngredient() + ", ";
        }
        return getResources().getString(R.string.Ingrediendts_Needed_Start) + neededIngredients;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LINEAR_LAYOUT_POSITION, linearLayoutManager.onSaveInstanceState());
        outState.putSerializable(MainActivity.RECIPE_OBJECT, CurrentRecipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null) {
            LinearLayoutSaved = savedInstanceState.getParcelable(LINEAR_LAYOUT_POSITION);
            CurrentRecipe = (BakingRepo) savedInstanceState.getSerializable(MainActivity.RECIPE_OBJECT);
        }
    }
}
