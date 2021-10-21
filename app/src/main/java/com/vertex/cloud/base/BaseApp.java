package com.vertex.cloud.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.jess.arms.base.App;
import com.jess.arms.di.component.AppComponent;

public class BaseApp extends Application implements App {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return null;
    }
}
