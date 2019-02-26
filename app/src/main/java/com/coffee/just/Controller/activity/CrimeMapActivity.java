package com.coffee.just.Controller.activity;

import android.os.Bundle;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.coffee.just.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CrimeMapActivity extends AppCompatActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient;
    private LocationClientOption option;
    private MyLocationData locationData;
    private MyLocationConfiguration mMyLocationConfiguration;
    boolean flag =true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_baidu_map);
         SDKInitializer.initialize(this);
         SDKInitializer.setCoordType(CoordType.BD09LL);
         mMapView = findViewById(R.id.activity_crime_baidu_map);
         mBaiduMap = mMapView.getMap();
         mBaiduMap.setMyLocationEnabled(true);
        UiSettings uiSettings = mBaiduMap.getUiSettings();

        //获取定位信息
        locationClient  = new LocationClient(getApplicationContext());
        option= new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        locationClient.setLocOption(option);

        uiSettings.setCompassEnabled(true);
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        locationClient.start();

        mMyLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING,true,null);
        mBaiduMap.setMyLocationConfiguration(mMyLocationConfiguration);

        BaiduMap.OnMapStatusChangeListener  listener = new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                mMyLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
                mBaiduMap.setMyLocationConfiguration(mMyLocationConfiguration);
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

            }
        };
        mBaiduMap.setOnMapStatusChangeListener(listener);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {

        locationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView=null;
        super.onDestroy();
    }

    class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation == null || mMapView == null) {
                return;
            }
             locationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(bdLocation.getDirection())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
                    mBaiduMap.setMyLocationData(locationData);
        }
    }



    }

