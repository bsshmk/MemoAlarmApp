package com.mksoft.memoalarmapp.component.activity.fragment.memoItemViewFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mksoft.memoalarmapp.R;
import com.mksoft.memoalarmapp.component.activity.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

public class MemoItemViewFragment extends Fragment implements MainActivity.onKeyBackPressedListener {

    MainActivity mainActivity;

    TextView tvTitle, tvContent, tvRegDate, tvEndDate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnKeyBackPressedListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.memo_item_view, container, false);
        this.configureDagger();

        init(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackKey();
            }
        });

        return rootView;
    }

    public void init(ViewGroup view){
        mainActivity = (MainActivity) getActivity();

        tvTitle = view.findViewById(R.id.tvTitle);
        tvContent = view.findViewById(R.id.tvContent);
        tvRegDate = view.findViewById(R.id.tvRegDate);
        tvEndDate = view.findViewById(R.id.tvDeadline);

        tvTitle.setText("제목 : " + getArguments().getString("title"));
        tvRegDate.setText("등록일 : " + getArguments().getString("regDate"));
        tvEndDate.setText("마감일 : "+getArguments().getString("endDate"));
        tvContent.setText(getArguments().getString("content"));
    }

    @Override
    public void onBackKey() {
        mainActivity = (MainActivity) getActivity();
        mainActivity.setOnKeyBackPressedListener(null);
        mainActivity.onBackPressed();
    }
}