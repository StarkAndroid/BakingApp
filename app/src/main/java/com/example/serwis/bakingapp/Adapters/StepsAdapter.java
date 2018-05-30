package com.example.serwis.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serwis.bakingapp.Network.steps;
import com.example.serwis.bakingapp.R;

import java.util.List;

/**
 * Created by serwis on 2018-05-22.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    Context context;
    List<steps> stepsList;
    OnItemClickListener mOnItemClickListener;


    public StepsAdapter(Context context, List<steps> steps){
        this.context = context;
        this.stepsList = steps;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_step_overview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, int position) {
        steps CurrentStep = stepsList.get(position);
        String shortDescription = CurrentStep.getShortDescription();
        TextView shortDescTextView = holder.mStepTextView;
        shortDescTextView.setText(shortDescription);
    }

    @Override
    public int getItemCount() {
        if(stepsList ==null)return 0;
        else return stepsList.size();
    }

    public interface OnItemClickListener{
        void OnItemClick(View itemView, int position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mStepTextView;
        public ViewHolder(final View itemView) {
            super(itemView);
            mStepTextView = itemView.findViewById(R.id.Step_Overview_Text_View);

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
