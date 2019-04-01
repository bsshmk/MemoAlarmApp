package com.mksoft.memoalarmapp.DB;


import com.mksoft.memoalarmapp.DB.data.MemoData;
import com.mksoft.memoalarmapp.DB.data.OptionData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface OptionDataDao {
    @Query("SELECT * FROM option_data WHERE id = 0")
    OptionData getOptionData();


    @Insert(onConflict = REPLACE)
    void insertOPtion(OptionData optionData);

    @Query("SELECT COUNT(*) FROM option_data")
    int getCnt();
}
