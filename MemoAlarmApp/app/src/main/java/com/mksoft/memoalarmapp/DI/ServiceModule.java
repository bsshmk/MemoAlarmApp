package com.mksoft.memoalarmapp.DI;

import com.mksoft.memoalarmapp.component.service.Alarm.Service.AlarmService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {
    @ContributesAndroidInjector
    abstract AlarmService contributeAlarmService();
}
