package com.example.demo1.BroadcastReceiver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class BlueToothStartedReceiver extends BroadcastReceiver {
    private BlueToothFoundReceiver blueToothFoundReceiver;
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {

        }else if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
            Toast.makeText(context,"搜索完毕", Toast.LENGTH_SHORT).show();
        }else if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
            Toast.makeText(context,"搜索开始", Toast.LENGTH_SHORT).show();
//            context.registerReceiver(blueToothFoundReceiver,new IntentFilter("ndroid.bluetooth.device.action.FOUND"));
        }
        }
}

