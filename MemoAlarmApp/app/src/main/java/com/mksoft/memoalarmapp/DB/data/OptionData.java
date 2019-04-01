package com.mksoft.memoalarmapp.DB.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "option_data")
public class OptionData {
    public OptionData(){

    }
    public void setId(@NonNull int id) {
        this.id = id;
    }
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id = 0;

    @NonNull
    public int getId() {
        return id;
    }

    @ColumnInfo(name = "sleep_start_time")
    private int sleepStartTime;

    @ColumnInfo(name = "sleep_end_time")
    private int sleepEndTime;



    public int getSleepStartTime() {
        return sleepStartTime;
    }

    public int getSleepEndTime() {
        return sleepEndTime;
    }

    public void setSleepStartTime(int sleepStartTime) {
        this.sleepStartTime = sleepStartTime;
    }

    public void setSleepEndTime(int sleepEndTime) {
        this.sleepEndTime = sleepEndTime;
    }
}
