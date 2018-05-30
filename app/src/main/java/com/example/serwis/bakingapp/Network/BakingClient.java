package com.example.serwis.bakingapp.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by serwis on 2018-05-21.
 */

public interface BakingClient {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<BakingRepo>> cakesForUser();

}
