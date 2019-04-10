package com.mksoft.memoalarmapp.component.activity.fragment.memoBody;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksoft.memoalarmapp.R;
import com.mksoft.memoalarmapp.DB.data.MemoData;
import com.mksoft.memoalarmapp.component.activity.MainActivity;
import com.mksoft.memoalarmapp.component.activity.fragment.memoItemViewFragment.MemoItemViewFragment;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


public class MemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView registDateTextView;
        TextView endDateTextView;
        TextView memoTextView;
        TextView memoTitleTextView;

        MyViewHolder(View view){
            super(view);
            registDateTextView = view.findViewById(R.id.registDateTextView);
            endDateTextView = view.findViewById(R.id.endDateTextView);
            memoTextView = view.findViewById(R.id.memoTextView);
            memoTitleTextView = view.findViewById(R.id.memoTitleTextView);
        }
    }
    List<MemoData> items =  Collections.emptyList();
    Context context;

    MyViewHolder myViewHolder;
    MemoSortFunction memoSortFunction;

    public MemoAdapter(Context context){
        this.context = context;
        this.memoSortFunction = new MemoSortFunction();

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_body_item, parent, false);

        return new MyViewHolder(v);
    }

    Bundle bundle;
    MemoItemViewFragment memoItemViewFragment;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        myViewHolder = (MyViewHolder) holder;
        myViewHolder.registDateTextView.setText(items.get(position).getRegistDateTextView());
        myViewHolder.endDateTextView.setText(items.get(position).getEndDateTextView());
        myViewHolder.memoTextView.setText(items.get(position).getMemoText());
        myViewHolder.memoTitleTextView.setText(items.get(position).getMemoTitle());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putString("title", items.get(position).getMemoTitle());
                bundle.putString("content", items.get(position).getMemoText());
                bundle.putString("regDate", items.get(position).getRegistDateTextView());
                bundle.putString("endDate", items.get(position).getEndDateTextView());

                memoItemViewFragment = new MemoItemViewFragment();
                memoItemViewFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction;

                fragmentTransaction = MainActivity.mainActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, memoItemViewFragment,null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }//밀어서 삭제 (스와이브 삭제 용)

    public void endDateSort(){
        memoSortFunction.endDateSort(items);
        notifyItemRangeChanged(0,getItemCount());

    }
    public void registDateSort(){
        memoSortFunction.registDateSort(items);
        notifyItemRangeChanged(0,getItemCount());
    }
    public void refreshItem(List<MemoData> items){
        this.items = items;
        notifyDataSetChanged();
    }
    public MemoData getItem(int idx){
        return items.get(idx);
    }
    public List<MemoData> getAllItem(){return items;}
}//리스트뷰에 필요한 어뎁터를 만들어주는 공간이다.
