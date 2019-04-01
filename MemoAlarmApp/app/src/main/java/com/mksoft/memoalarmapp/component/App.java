package com.mksoft.memoalarmapp.component;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;


import com.mksoft.memoalarmapp.DI.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;


public class App extends Application implements HasActivityInjector, HasServiceInjector {
    public static Context context;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> dispatchingAndroidInjectorService;


    @Override
    public void onCreate() {
        super.onCreate();
        this.initDagger();
        context = getApplicationContext();
    }



    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public DispatchingAndroidInjector<Service> serviceInjector() {

        return dispatchingAndroidInjectorService;
    }

    private void initDagger(){

        DaggerAppComponent.builder().application(this).build().inject(this);
    }

}
