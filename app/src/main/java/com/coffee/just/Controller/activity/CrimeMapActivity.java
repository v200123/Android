package com.coffee.just.Controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import java.util.zip.Inflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CrimeMapActivity extends AppCompatActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient locationClient;
    private LocationClientOption option;
    private MyLocationData locationData;
    private MyLocationConfiguration mMyLocationConfiguration;
    private IntentFilter mIntentFilter;
    private NetworkChangeReceiver mNetworkChangeReceiver;
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

        mIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkChangeReceiver,mIntentFilter);

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
    protected void onStart() {
        super.onStart();


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
        unregisterReceiver(mNetworkChangeReceiver);
        mMapView.onDestroy();
        mMapView=null;
        super.onDestroy();
    }

    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "网络可用，导航开始",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(context, "网络不可用",
                        Toast.LENGTH_SHORT).show();
            }
        }
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

