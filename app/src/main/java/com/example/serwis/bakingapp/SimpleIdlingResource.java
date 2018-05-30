package com.example.serwis.bakingapp;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by serwis on 2018-05-30.
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable private volatile ResourceCallback mCallback;
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);


    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setIdlingState (boolean idlingState){
        mIsIdleNow.set(idlingState);
        if (idlingState && mCallback!=null){
            mCallback.onTransitionToIdle();
        }
    }
}
