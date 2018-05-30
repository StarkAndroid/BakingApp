package com.example.serwis.bakingapp.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serwis.bakingapp.Adapters.StepsAdapter;
import com.example.serwis.bakingapp.Network.BakingRepo;
import com.example.serwis.bakingapp.R;
import com.example.serwis.bakingapp.Network.steps;

import java.util.List;

/**
 * Created by serwis on 2018-05-27.
 */

public class MasterListFragment extends Fragment {


    public MasterListFragment(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_detail, container, false);

        return rootView;
    }
}
