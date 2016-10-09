package com.liu.wheeldemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.liu.wheeldemo.R;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnTimer;
    private Button mBtnCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
        initListener();
    }


    private void initView() {

        mBtnTimer = (Button) findViewById(R.id.btnTimer);
        mBtnCity = (Button) findViewById(R.id.btnCity);
    }

    private void initListener() {
        mBtnTimer.setOnClickListener(this);
        mBtnCity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnTimer:

                Intent intent = new Intent(this, TimerActivity.class);
                startActivity(intent);

                break;
            case R.id.btnCity:

                Intent cityIntent = new Intent(this, CitysActivity.class);
                startActivity(cityIntent);

                break;
        }
    }
}
