package com.example.demo1.controller;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.demo1.BroadcastReceiver.BluetoothStateReceiver;

public class BlueToothController {
    private BluetoothStateReceiver bluetoothStateReceiver;
    private static BlueToothController instance ;
    public static final BlueToothController getInstance(){
        if (instance == null){
            instance = new BlueToothController();
        }
        return instance;

    }

    // 成员变量
    private BluetoothAdapter mAdapter;
    /**
     * 构造函数
     */
    public BlueToothController(){
        // 获取本地的蓝牙适配器
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    public boolean isBlueToothOpen(){
        // 判断蓝牙是否打开
        return mAdapter.isEnabled();
    }
    public boolean isBlueToothSupport(){
        if (mAdapter == null){
            return false;
        }else {
            return true;
        }
    }
    public boolean getBlueToothState(){
        assert (mAdapter != null);
        return mAdapter.isEnabled();
    }



}
