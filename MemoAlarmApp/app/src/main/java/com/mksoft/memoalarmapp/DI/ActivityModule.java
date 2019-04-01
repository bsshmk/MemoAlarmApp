package com.mksoft.memoalarmapp.DI;


import com.mksoft.memoalarmapp.component.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Philippe on 02/03/2018.
 */


@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector//그 액티비티의 연결된 플레그먼트의 모듈 자신보다 아래에 있는 친구
    abstract MainActivity contributeMainActivity();//여러개의 액티비티중에 그중에 MainAcrivity를 주입



}
