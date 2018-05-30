package com.example.serwis.bakingapp.Network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by serwis on 2018-05-21.
 */

public class BakingRepo implements Serializable {

    private String name;
    private String id;
    List<ingredients> ingredients;
    List<steps> steps;

    public String getId() {
        return id;
    }

    public List<com.example.serwis.bakingapp.Network.steps> getSteps() {
        return steps;
    }

    public BakingRepo () {
        ingredients = new ArrayList<>();
    }

    public List<ingredients> getIngredients() {
        return ingredients;
    }

    public String getName() {
        return name;
    }
}
