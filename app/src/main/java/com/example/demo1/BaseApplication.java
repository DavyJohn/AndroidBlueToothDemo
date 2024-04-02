package com.example.demo1;

import android.Manifest;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.demo1.BroadcastReceiver.BluetoothOpenReceiver;
import com.example.demo1.BroadcastReceiver.BluetoothStateReceiver;

import java.util.ArrayList;
import java.util.logging.Logger;

public class BaseApplication  extends Application {
    private BluetoothStateReceiver bluetoothStateReceiver;
    private BluetoothOpenReceiver bluetoothOpenReceiver;
    private static BaseApplication instance ;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        Log.e("BaseApplication", "onCreate");

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



}
