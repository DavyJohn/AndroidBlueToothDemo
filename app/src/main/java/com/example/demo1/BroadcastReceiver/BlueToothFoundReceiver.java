package com.example.demo1.BroadcastReceiver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class BlueToothFoundReceiver extends BroadcastReceiver {
    private List<String> addressList;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.bluetooth.device.action.FOUND")){
            addressList = new ArrayDeque<>();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            @SuppressLint("MissingPermission")
            String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress(); // MAC address
            addressList.add(deviceHardwareAddress);
            Toast.makeText(context, "deviceName: " + deviceName + " deviceHardwareAddress: " + deviceHardwareAddress, Toast.LENGTH_SHORT).show();
        }

    }
}
