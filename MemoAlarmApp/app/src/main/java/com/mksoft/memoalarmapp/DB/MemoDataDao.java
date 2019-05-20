package com.mksoft.memoalarmapp.DB;




import com.mksoft.memoalarmapp.DB.data.MemoData;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface MemoDataDao {

    @Query("SELECT * FROM memo_data_table")
    LiveData<List<MemoData>> getAll();

    @Query("SELECT * FROM memo_data_table")
    List<MemoData> getStaticAll();


    @Insert(onConflict = REPLACE)
    void insertMemo(MemoData memoData);

    @Query("DELETE from memo_data_table")
    void deleteAll();

    @Delete
    void delete(MemoData memoData);

    @Query("SELECT * FROM memo_data_table ORDER BY regist_date")
    LiveData<List<MemoData>> registDateSort();

    @Query("SELECT * FROM memo_data_table ORDER BY end_date")
    LiveData<List<MemoData>> endDateSort();


}
