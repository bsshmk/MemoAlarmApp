package com.mksoft.memoalarmapp.component.activity;


import android.content.Intent;
import android.os.Bundle;

import com.mksoft.memoalarmapp.component.activity.fragment.MemoOverallSetting.MemoOverallSettingFragment;
import com.mksoft.memoalarmapp.component.service.Alarm.Service.AlarmService;
import com.mksoft.memoalarmapp.otherMethod.HideKeyboard;
import com.mksoft.memoalarmapp.R;
import com.mksoft.memoalarmapp.component.activity.fragment.memoAdd.MemoAddFragment;
import com.mksoft.memoalarmapp.component.activity.fragment.memoBody.MemoBodyFragment;
import com.mksoft.memoalarmapp.component.activity.fragment.memoTimeSetting.MemoTimeSettingFragment;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MainActivity extends AppCompatActivity  implements HasSupportFragmentInjector {

    MemoBodyFragment memoBodyFragment;
    MemoAddFragment memoAddFragment;
    MemoTimeSettingFragment memoTimeSettingFragment;//플레그먼트 주입이 될까?
    HideKeyboard hideKeyboard;
    public static MainActivity mainActivity;
    MemoOverallSettingFragment memoOverallSettingFragment;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAlarmService();
        mainActivity = this;
        init();
        this.configureDagger();
    }
    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    private void configureDagger(){
        AndroidInjection.inject(this);
    }


    private void init(){
        hideKeyboard = new HideKeyboard(this);
        memoBodyFragment = new MemoBodyFragment();
        memoAddFragment = new MemoAddFragment();
        memoTimeSettingFragment = new MemoTimeSettingFragment();
        memoOverallSettingFragment = new MemoOverallSettingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoBodyFragment).commit();
    }
    public void OnFragmentChange(int idx, Bundle bundle){
        if(idx == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoBodyFragment).commit();
        }else if(idx == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoAddFragment).commit();
        }else if(idx == 2){
            memoTimeSettingFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoTimeSettingFragment).commit();

        }else if(idx == 3){
            getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoOverallSettingFragment).commit();
        }
    }

    public void startAlarmService(){
        Intent intent = new Intent(this,AlarmService.class);
        startService(intent);
    }
    public HideKeyboard getHideKeyboard(){
        return hideKeyboard;
    }


}
