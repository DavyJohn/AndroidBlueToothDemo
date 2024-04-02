package com.example.demo1.BroadcastReceiver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class BlueToothFoundReceiver extends BroadcastReceiver {
    private BluetoothAdapter bluetoothAdapter;

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//            if (device.getBondState() != BluetoothDevice.BOND_BONDED){
//
//            }

            Log.d("BlueToothFoundReceiver", "onReceive: " + device.getName() + " " + device.getAddress());
        }else if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
            Toast.makeText(context,"搜索完毕", Toast.LENGTH_SHORT).show();
        }else if (intent.getAction().equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
            Toast.makeText(context,"搜索开始", Toast.LENGTH_SHORT).show();
        }
        }
}

