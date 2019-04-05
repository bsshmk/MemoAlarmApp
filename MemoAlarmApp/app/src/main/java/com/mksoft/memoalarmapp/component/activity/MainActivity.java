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
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MainActivity extends AppCompatActivity  implements HasSupportFragmentInjector {

    MemoBodyFragment memoBodyFragment;
    HideKeyboard hideKeyboard;
    public static MainActivity mainActivity;

    BackPressCloseHandler backPressCloseHandler;

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
        backPressCloseHandler = new BackPressCloseHandler(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, memoBodyFragment).commit();
    }


    public void startAlarmService(){
        Intent intent = new Intent(this,AlarmService.class);
        startService(intent);
    }
    public HideKeyboard getHideKeyboard(){
        return hideKeyboard;
    }

    ////////////////////// back key

    public interface onKeyBackPressedListener{
        void onBackKey();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener){
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBackKey();
        }else{
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                backPressCloseHandler.onBackPressed();
            }
            else{
                super.onBackPressed();
            }
        }
    }


}
