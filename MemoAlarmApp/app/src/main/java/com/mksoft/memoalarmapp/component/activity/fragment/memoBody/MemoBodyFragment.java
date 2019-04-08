package com.mksoft.memoalarmapp.component.activity.fragment.memoBody;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mksoft.memoalarmapp.DB.MemoReposityDB;
import com.mksoft.memoalarmapp.DB.data.OptionData;
import com.mksoft.memoalarmapp.ViewModel.MemoViewModel;
import com.mksoft.memoalarmapp.component.activity.MainActivity;
import com.mksoft.memoalarmapp.R;
import com.mksoft.memoalarmapp.DB.data.MemoData;
import com.mksoft.memoalarmapp.component.activity.fragment.MemoOverallSetting.MemoOverallSettingFragment;
import com.mksoft.memoalarmapp.component.activity.fragment.memoAdd.MemoAddFragment;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.InvalidationTracker;
import dagger.android.support.AndroidSupportInjection;

public class MemoBodyFragment extends Fragment {
    public MemoBodyFragment(){

    }
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    MemoAdapter memoAdapter;
    Button sortButton;
    Button addButton;

    MainActivity mainActivity;
    AlertDialog dialog;
    final CharSequence[] howWrite = {"등록일", "마감일"};

    private MemoViewModel memoViewModel;

    ItemTouchHelper itemTouchHelper;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    FragmentTransaction fragmentTransaction;
    @Inject
    MemoReposityDB memoReposityDB;

    MemoSortFunction memoSortFunction;

    int sortFlag = 0;//0 = 등록일, 1 = 마감일

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnKeyBackPressedListener(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureViewModel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureDagger();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_setting:
                Toast.makeText(getContext(), "setting page", Toast.LENGTH_SHORT).show();
//
                fragmentTransaction = MainActivity.mainActivity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, new MemoOverallSettingFragment(),null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                changeFragment(3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void configureDagger(){

        AndroidSupportInjection.inject(this);
    }
    private void configureViewModel(){
        memoViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemoViewModel.class);
        memoViewModel.init();

        memoViewModel.getMemoDataLiveData().observe(this, memoDataLiveData -> {
            if(sortFlag == 0){
                memoSortFunction.registDateSort(memoDataLiveData);
            }else if(sortFlag == 1){
                memoSortFunction.endDateSort(memoDataLiveData);
            }
            refreshDB(memoDataLiveData);
        });//라이브데이터를 바꾸거나 내부 저장된 디비의 배열을 변경하고 싶었지만... 잘 안됨...
        //그래서 플래그 값을 주고 그에 맞추어서 옵저브가 변경을 인지할 때 마다 정렬을 해주는 식으로 구현

    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.memo_body, container, false);

        init(rootView);

        clickSortButton();
        clickAddButton();

        hideKeyboard();

        return rootView;
    }

    private void init(ViewGroup rootView){
        if(memoReposityDB.getOptionData() == null){
            OptionData optionData = new OptionData();
            optionData.setSleepStartTime(23);
            optionData.setSleepEndTime(9);
            memoReposityDB.insertOption(optionData);

        }

        memoSortFunction = new MemoSortFunction();
        mainActivity = (MainActivity) getActivity();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.memoListRecyclerView);
        layoutManager = new LinearLayoutManager(rootView.getContext());

        addButton = (Button)rootView.findViewById(R.id.addButton);
        sortButton = (Button)rootView.findViewById(R.id.sortButton);


        makeDialog(mainActivity);
        initListView();

    }


    private void initListView(){



            recyclerView.setHasFixedSize(true);

            memoAdapter = new MemoAdapter(getContext());
            recyclerView.setAdapter(memoAdapter);
            recyclerView.setLayoutManager(layoutManager);
            itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(memoAdapter, memoReposityDB));
            itemTouchHelper.attachToRecyclerView(recyclerView);

    }







    private void clickSortButton(){
        if(sortButton == null)
            return;
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });
    }



    private void clickAddButton(){
        if(addButton == null)
            return;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                MainActivity.mainActivity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.mainContainer, new MemoAddFragment(), null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    private void makeDialog(MainActivity mainActivity){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("정렬 방법을 선택하세요.");
        builder.setItems(howWrite, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){

                    sortFlag = 0;
                    memoAdapter.registDateSort();
                }else{

                    sortFlag = 1;
                    memoAdapter.endDateSort();
                }//정렬을 외부에서 하고 다시 리사이크러뷰를 초기화해주는 병신같은 짓은 하지 말자.

            }
        });
        dialog = builder.create();    // 알림창 객체 생성

    }


    private void hideKeyboard(){
        MainActivity.mainActivity.getHideKeyboard().hideKeyboard();
    }

    public void refreshDB(@Nullable List<MemoData> memoDataList){

        memoAdapter.refreshItem(memoDataList);

    }








}
