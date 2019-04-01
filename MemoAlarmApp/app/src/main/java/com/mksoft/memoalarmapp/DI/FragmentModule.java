package com.mksoft.memoalarmapp.DI;


import com.mksoft.memoalarmapp.component.activity.fragment.MemoOverallSetting.MemoOverallSettingFragment;
import com.mksoft.memoalarmapp.component.activity.fragment.memoAdd.MemoAddFragment;
import com.mksoft.memoalarmapp.component.activity.fragment.memoBody.MemoBodyFragment;
import com.mksoft.memoalarmapp.component.activity.fragment.memoTimeSetting.MemoTimeSettingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Philippe on 02/03/2018.
 */

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract MemoOverallSettingFragment contributeMemoOverallSetting();

    @ContributesAndroidInjector
    abstract MemoAddFragment contributeMemoAddFragment();

    @ContributesAndroidInjector
    abstract MemoBodyFragment contributeMemoBodyFragment();

    @ContributesAndroidInjector
    abstract MemoTimeSettingFragment contributeMemoTimeSettingFragment();


}
