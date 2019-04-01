package com.mksoft.memoalarmapp.component.activity.fragment.memoBody;

import com.mksoft.memoalarmapp.DB.data.MemoData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MemoSortFunction {
    public MemoSortFunction() {
    }
    public List<MemoData> registDateSort(List<MemoData> inputData){
        Collections.sort(inputData, new Comparator<MemoData>() {
            @Override
            public int compare(MemoData b1, MemoData b2) {
                return b1.getRegistDateTextView().compareTo(b2.getRegistDateTextView());
            }
        });


        return inputData;
    }
    public List<MemoData> endDateSort(List<MemoData> inputData){

        Collections.sort(inputData, new Comparator<MemoData>() {
            @Override
            public int compare(MemoData b1, MemoData b2) {
                return b1.getEndDateTextView().compareTo(b2.getEndDateTextView());
            }
        });
        return inputData;
    }

}
