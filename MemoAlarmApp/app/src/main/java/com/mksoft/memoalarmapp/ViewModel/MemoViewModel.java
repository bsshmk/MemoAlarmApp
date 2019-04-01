package com.mksoft.memoalarmapp.ViewModel;

import android.util.Log;

import com.mksoft.memoalarmapp.DB.MemoReposityDB;
import com.mksoft.memoalarmapp.DB.data.MemoData;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MemoViewModel extends ViewModel {
    private LiveData<List<MemoData>> memoDataLiveData;
    private MemoReposityDB memoReposityDB;

    @Inject
    public MemoViewModel(MemoReposityDB memoReposityDB){
        Log.d("testViewModel", "make it !");
        this.memoReposityDB = memoReposityDB;
        init();
    }

    public void init(){
        if(this.memoDataLiveData != null){
            return;
        }
        memoDataLiveData = memoReposityDB.getMemoDataList();

    }
    public LiveData<List<MemoData>> getMemoDataLiveData(){

        return this.memoDataLiveData;
    }
    public void deleteMemoData(MemoData memoData){

        memoReposityDB.deleteMemo(memoData);

    }
    public void insertMemoData(MemoData memoData){
        memoReposityDB.insertMemo(memoData);

    }
}
