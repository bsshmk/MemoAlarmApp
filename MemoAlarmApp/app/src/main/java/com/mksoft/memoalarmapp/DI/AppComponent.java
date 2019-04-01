package com.mksoft.memoalarmapp.DI;


import android.app.Application;

import com.mksoft.memoalarmapp.component.App;

import com.mksoft.memoalarmapp.DI.ActivityModule;
import com.mksoft.memoalarmapp.DI.AppModule;
import com.mksoft.memoalarmapp.DI.ServiceModule;
import com.mksoft.memoalarmapp.DI.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;



@Singleton
@Component(modules={AndroidSupportInjectionModule.class, ActivityModule.class,
        FragmentModule.class, AppModule.class, ServiceModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);
}
