package com.liu.wheeldemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liu.wheeldemo.R;
import com.liu.wheeldemo.adapter.AddressTextAdapter;
import com.liu.wheeldemo.bean.RegionJson;
import com.liu.wheeldemo.util.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import static com.liu.wheeldemo.R.id.city;

public class CitysActivity extends AppCompatActivity implements View.OnClickListener {

    private WheelView mProvince;
    private WheelView mCity;
    private WheelView mArea;
    private Button    mBtnGet;



    /**
     * key - 省 value - 市s
     */
    private Map<String, List<String>> mCitisDatasMap = new HashMap<String, List<String>>();
    /**
     * key - 市 values - 区s
     */
    private Map<String, List<String>> mAreaDatasMap  = new HashMap<String, List<String>>();


    // Scrolling flag
    private boolean proviceScrolling = false;
    private boolean cityScrolling = false;
    private AddressTextAdapter mProviceAdapter;
    private AddressTextAdapter mCityAdapter;
    private AddressTextAdapter mAreaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citys);
        initView();
        initData();
        initListener();
    }

    private void initView() {

        mProvince = (WheelView) findViewById(R.id.province);
        mCity = (WheelView) findViewById(city);
        mArea = (WheelView) findViewById(R.id.area);
        mBtnGet = (Button) findViewById(R.id.btnGet);

    }

    private void initData() {

        //读取文件
        String str = FileUtils.readAssetsFile(this, "china-city-area-zip.min.json");

        Log.d("vivi", "initData:  " + str);

        //转化为对象集合
        List<RegionJson> datas = JSON.parseArray(str, RegionJson.class);

        Log.d("vivi", "initData: " + datas.size());

        initAddress(datas);

    }

    private void initAddress(List<RegionJson> datas) {

        List<String> mProvinceList = new ArrayList<>();
        for(RegionJson data : datas) {


            //省
            mProvinceList.add(data.name);

            List<String> mCitysList    = new ArrayList<>();

            for(RegionJson.ChildEntity city : data.child) {

                //市

                mCitysList.add(city.name);
                List<String> mAreaList     = new ArrayList<>();
                for(RegionJson.ChildEntity.ChildEntity2 area : city.child) {
                    //区

                    mAreaList.add(area.name);
                }

                mAreaDatasMap.put(city.name,mAreaList);


            }
            mCitisDatasMap.put(data.name,mCitysList);


        }

        /**
         *  省
         */

        mProvince.setVisibleItems(5);

        mProviceAdapter = new AddressTextAdapter(this, mProvinceList);
        mProvince.setViewAdapter(mProviceAdapter);
        mProvince.setCurrentItem(0);

        /**
         * 市
         */
        mCity.setVisibleItems(5);

        mCityAdapter = new AddressTextAdapter(this, mCitisDatasMap.get(mProvinceList.get(0)));
        mCity.setViewAdapter(mCityAdapter);
        mCity.setCurrentItem(0);

        /**
         * 地区
         */

        mArea.setVisibleItems(5);

        mAreaAdapter = new AddressTextAdapter(this, mAreaDatasMap.get(mCitisDatasMap.get(mProvinceList.get(0)).get(0)));
        mArea.setViewAdapter(mAreaAdapter);
        mArea.setCurrentItem(0);


    }

    private void initListener() {


        initScrllListener();

        initProviceChangeListener();


        initCityChangeListener();

        mBtnGet.setOnClickListener(this);

    }

    private void initScrllListener() {
        mProvince.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                proviceScrolling =true;
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

                proviceScrolling =false;
                //省滚动完成后切换市
                updateCities(mCity,mCitisDatasMap.get(mProviceAdapter.getName(mProvince.getCurrentItem())));
                updateArea(mArea,mAreaDatasMap.get(mCityAdapter.getName(mCity.getCurrentItem())));

            }
        });

        /**
         * 市滚东中
         */
        mCity.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
                cityScrolling=true;
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                cityScrolling =false;


                //市滚动完成后切换
               updateArea(mArea,mAreaDatasMap.get(mCityAdapter.getName(mCity.getCurrentItem())));

            }
        });
    }


    /**
     * 省滚动中切换
     */
    private void initProviceChangeListener() {

        /**
         * 省滚动中切换市
         */
        mProvince.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                //Log.d("vivi", "onChanged:  oldValue  "+ oldValue+ "  newValue  "+newValue);
               // List<String> test = mCitisDatasMap.get(mProviceAdapter.getName(newValue));

                if(proviceScrolling){

                    updateCities(mCity,mCitisDatasMap.get(mProviceAdapter.getName(newValue)));


                        updateArea(mArea,mAreaDatasMap.get(mCityAdapter.getName(mCity.getCurrentItem())));

                }

            }

        });

    }


    /**
     * 切换城市
     */
    private void updateCities(WheelView city, List<String> cities) {

        mCityAdapter = new AddressTextAdapter(this,cities);

        city.setViewAdapter(mCityAdapter);
        city.setCurrentItem(0);
    }


    /**
     * 市滚动中切换地区
     */
    private void initCityChangeListener() {


        mCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                if(cityScrolling){
                    updateArea(mArea,mAreaDatasMap.get(mCityAdapter.getName(newValue)));
                }
            }
        });


    }

    private void updateArea(WheelView area,List<String> areas){
       mAreaAdapter= new AddressTextAdapter(this,areas);

        area.setViewAdapter(mAreaAdapter);
        area.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnGet:
                getAddress();
                break;
        }
    }

    private void getAddress() {

        String provice = mProviceAdapter.getName(mProvince.getCurrentItem());
        String city = mCityAdapter.getName(mCity.getCurrentItem());
        String area = mAreaAdapter.getName(mArea.getCurrentItem());

        Toast.makeText(this, " 地址 ："+ provice+city+area, Toast.LENGTH_SHORT).show();

    }
}
