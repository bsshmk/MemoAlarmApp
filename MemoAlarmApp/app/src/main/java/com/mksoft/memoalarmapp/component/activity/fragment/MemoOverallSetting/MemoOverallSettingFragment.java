package com.mksoft.memoalarmapp.component.activity.fragment.MemoOverallSetting;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mksoft.memoalarmapp.DB.MemoReposityDB;
import com.mksoft.memoalarmapp.DB.data.OptionData;
import com.mksoft.memoalarmapp.R;
import com.mksoft.memoalarmapp.component.activity.MainActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

public class MemoOverallSettingFragment extends Fragment {
    public MemoOverallSettingFragment(){

    }
    MainActivity mainActivity;

    Button backBtn;
    NumberPicker np1, np2;

    RelativeLayout relativeLayout;

    int startTime, endTime;
    OptionData optionData = null;

    @Inject
    MemoReposityDB memoReposityDB;



    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.configureDagger();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.memo_overall_setting, container, false);
        init(rootView);

        hideKeyboard();
        clickHideKeyboard();

        // save and go back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        return rootView;
    }

    public void init(ViewGroup viewGroup){


        optionData = memoReposityDB.getOptionData();
        relativeLayout = (RelativeLayout) viewGroup.findViewById(R.id.settingLayout2);
        backBtn = (Button) viewGroup.findViewById(R.id.settingPageBackButton);

        np1 = (NumberPicker) viewGroup.findViewById(R.id.np1);
        np2 = (NumberPicker) viewGroup.findViewById(R.id.np2);

        np1.setMinValue(0);
        np1.setMaxValue(23);

        np2.setMinValue(0);
        np2.setMaxValue(23);

        if(optionData == null){

            np1.setValue(23);
            np2.setValue(9);
            optionData = new OptionData();
        }else{

            np1.setValue(optionData.getSleepStartTime());
            np2.setValue(optionData.getSleepEndTime());
        }
    }

    public void saveData(){
        startTime = np1.getValue();
        endTime = np2.getValue();

        optionData.setSleepStartTime(startTime);
        optionData.setSleepEndTime(endTime);

        Toast.makeText(getContext(), "updated!", Toast.LENGTH_SHORT).show();
        memoReposityDB.insertOption(optionData);
        mainActivity.OnFragmentChange(0,null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    public void changeFragment(int idx, ){
//        mainActivity.OnFragmentChange(0, null);//이 페이지에서 데이터 처리하고 널을 넘기자.
//    }

    private void hideKeyboard(){
        mainActivity.getHideKeyboard().hideKeyboard();
    }
    private void clickHideKeyboard(){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
            }
        });
    }

}

