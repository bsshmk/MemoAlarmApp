package com.mksoft.memoalarmapp.DB.data;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "memo_data_table")
public class MemoData implements Serializable {
    public void setId(@NonNull int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id = 0;


    @ColumnInfo(name = "regist_date")
    private String registDateTextView;
    @ColumnInfo(name = "memo_text")
    private String memoText;

    @ColumnInfo(name = "end_date")
    private String endDateTextView;

    @ColumnInfo(name = "min_time")
    private String minTime;

    @ColumnInfo(name = "memo_title")
    private String memoTitle;



    @ColumnInfo(name = "random_time")
    private String randomTime;

    public void setRandomTime(String randomTime) {
        this.randomTime = randomTime;
    }

    public String getRandomTime() {
        return randomTime;
    }

    public int getId() {
        return id;
    }

    public MemoData(){}


    public String getRegistDateTextView() {
        return registDateTextView;
    }

    public String getMemoText() {
        return memoText;
    }

    public String getEndDateTextView() {
        return endDateTextView;
    }

    public String getMinTime(){return minTime;}

    public void setRegistDateTextView(String registDateTextView) {
        this.registDateTextView = registDateTextView;
    }

    public void setMemoText(String memoText) {
        this.memoText = memoText;
    }

    public void setEndDateTextView(String endDateTextView) {
        this.endDateTextView = endDateTextView;
    }

    public void setMinTime(String minTime){
        this.minTime=minTime;
    }



    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

}
