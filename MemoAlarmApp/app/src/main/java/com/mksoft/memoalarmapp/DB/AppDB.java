package com.mksoft.memoalarmapp.DB;


import com.mksoft.memoalarmapp.DB.data.MemoData;
import com.mksoft.memoalarmapp.DB.data.OptionData;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MemoData.class, OptionData.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract MemoDataDao memoDataDao();
    public abstract OptionDataDao optionDataDao();
    private static volatile AppDB INSTANCE;//volatile 메모리에 접근 가능

}
