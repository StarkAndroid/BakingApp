package com.example.serwis.bakingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.serwis.bakingapp.Adapters.RecipeAdapter;
import com.example.serwis.bakingapp.Network.BakingRepo;
import com.example.serwis.bakingapp.Network.BakingClient;
import com.example.serwis.bakingapp.R;
import com.example.serwis.bakingapp.SimpleIdlingResource;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    String TestJSONfile;
    public static final String RECIPE_OBJECT = "RECIPE Object";
    String KEY_RV_POSITION = "RVposition";
    GridLayoutManager gridLayoutManager;
    Parcelable savedInstanceStateLayout;

    @Nullable
    private SimpleIdlingResource simpleIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingresouce(){
        if (simpleIdlingResource==null){
            simpleIdlingResource = new SimpleIdlingResource();
        } return simpleIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.RecipesRecyclerView);

        if (savedInstanceState!=null){
            savedInstanceStateLayout = savedInstanceState.getParcelable(KEY_RV_POSITION);
        }

        getIdlingresouce();

        if (simpleIdlingResource != null) {
            simpleIdlingResource.setIdlingState(false);
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        BakingClient client =  retrofit.create(BakingClient.class);

        Call<List<BakingRepo>> call = client.cakesForUser();

        call.enqueue(new Callback<List<BakingRepo>>() {
            @Override
            public void onResponse(Call<List<BakingRepo>> call, Response<List<BakingRepo>> response) {
                final List<BakingRepo> recipes = response.body();
                RecipeAdapter adapter = new RecipeAdapter(MainActivity.this, recipes);
                adapter.setmOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View itemView, int position) {
                        Intent newIntent = new Intent(MainActivity.this, DetailActivity.class);
                        newIntent.putExtra(RECIPE_OBJECT, recipes.get(position));
                        startActivity(newIntent);
                    }
                });
                mRecyclerView.setAdapter(adapter);
                int numberofC = calculateNoOfColumns(MainActivity.this);
                gridLayoutManager = new GridLayoutManager(MainActivity.this, numberofC);
                if (savedInstanceStateLayout!=null){
                    gridLayoutManager.onRestoreInstanceState(savedInstanceStateLayout);}
                mRecyclerView.setLayoutManager(gridLayoutManager);
                if (simpleIdlingResource != null) {
                    simpleIdlingResource.setIdlingState(true);
                }
            }

            @Override
            public void onFailure(Call<List<BakingRepo>> call, Throwable t) {

            }
        });



        RecipeAdapter adapter = new RecipeAdapter(this, null);
        mRecyclerView.setAdapter(adapter);
        int numberofC = calculateNoOfColumns(this);
        gridLayoutManager = new GridLayoutManager(this, numberofC);
        if (savedInstanceStateLayout!=null){
            gridLayoutManager.onRestoreInstanceState(savedInstanceStateLayout);}
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_RV_POSITION, gridLayoutManager.onSaveInstanceState());
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth>720) return 3;
        else return 1;
    }
}
