package com.example.serwis.bakingapp.Adapters;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serwis.bakingapp.Network.BakingRepo;
import com.example.serwis.bakingapp.R;

import java.util.List;

/**
 * Created by serwis on 2018-05-19.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    List<BakingRepo> recipeList;
    Context mContext;
    OnItemClickListener mOnItemClickListener;



    public RecipeAdapter (Context context, List<BakingRepo> recipes){
        mContext = context;
        recipeList = recipes;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {

        BakingRepo bakingRepo = recipeList.get(position);
        String BakingRecipeName = bakingRepo.getName();
        TextView textView = holder.mRecipeNameTextView;
        textView.setText(BakingRecipeName);

    }

    @Override
    public int getItemCount() {
        if (recipeList == null) return 0;
        else return recipeList.size();
    }

    public interface OnItemClickListener{
        void OnItemClick(View itemView, int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mRecipeNameTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mRecipeNameTextView = itemView.findViewById(R.id.RecipeNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener!=null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            mOnItemClickListener.OnItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}
