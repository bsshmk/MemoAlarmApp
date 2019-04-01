package com.mksoft.memoalarmapp.component.activity.fragment.memoBody;


import com.mksoft.memoalarmapp.DB.MemoReposityDB;
import com.mksoft.memoalarmapp.DB.data.MemoData;
import com.mksoft.memoalarmapp.ViewModel.MemoViewModel;
import com.mksoft.memoalarmapp.component.activity.MainActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

    private MemoAdapter mAdapter;
    private MemoReposityDB memoReposityDB;
    public ItemTouchHelperCallback(MemoAdapter adapter, final MemoReposityDB memoReposityDB) {
        super(0,  ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        this.memoReposityDB = memoReposityDB;
    }



    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        //MemoData temp = mAdapter.getItem(position);//어뎁터에서 삭제전에 미리 정보를 저장하고
        //mAdapter.deleteItem(position);//
        memoReposityDB.deleteMemo(mAdapter.getItem(position));//디비에서 지우자.

    }//스와이프 삭제를 위한 부분

}
//클릭시 수정페이지 만들어주기