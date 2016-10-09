package com.liu.wheeldemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liu.wheeldemo.R;
import com.liu.wheeldemo.adapter.TimerAdapter;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.WheelView;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private WheelView mWheelViewTimer;
    private Button mBtnOk;

    private List<String> mDatas = new ArrayList<>();
    private TimerAdapter mTimerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initView() {


        mWheelViewTimer = (WheelView) findViewById(R.id.wheelviewTimer);

        mBtnOk = (Button) findViewById(R.id.btnOk);


    }

    private void initData() {

        for(int i = 0; i <24 ; i++) {
            mDatas.add(i+"");
        }

        //设置可以见的条目数量， 上中下
        mWheelViewTimer.setVisibleItems(5);


        //设置适配器
        mTimerAdapter = new TimerAdapter(this,mDatas);

        mWheelViewTimer.setViewAdapter(mTimerAdapter);

        //放在最后面 设置当前显示的
        mWheelViewTimer.setCurrentItem(5);


    }

    private void initListener() {

        mBtnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnOk:
                getSelectText();
                break;

        }
    }

    private void getSelectText() {


        int currentItem = mWheelViewTimer.getCurrentItem();
        String str        = mDatas.get(currentItem);
        Toast.makeText(this,  str, Toast.LENGTH_SHORT).show();

    }


}
