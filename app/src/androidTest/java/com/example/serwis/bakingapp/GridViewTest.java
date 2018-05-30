package com.example.serwis.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.serwis.bakingapp.UI.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.Matchers.anything;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by serwis on 2018-05-29.
 */
@RunWith(AndroidJUnit4.class)
public class GridViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource simpleIdlingresource;

    @Before
    public void registerIdlingresource(){
        simpleIdlingresource = mainActivityActivityTestRule.getActivity().getIdlingresouce();
        Espresso.registerIdlingResources(simpleIdlingresource);
    }

    @Test
    public void clickGridViewItem(){
        onView(withId(R.id.RecipesRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void unregisterIdlingresource(){
        if (simpleIdlingresource != null ){
            Espresso.unregisterIdlingResources(simpleIdlingresource);
        }
    }

}
