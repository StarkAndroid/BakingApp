package com.example.serwis.bakingapp.Network;

import java.io.Serializable;

/**
 * Created by serwis on 2018-05-21.
 */

public class ingredients implements Serializable {
    private String ingredient;
    private String measure;
    private String quantity;

    public String getMeasure() {
        return measure;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }
}