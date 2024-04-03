package com.example.demo1.BroadcastReceiver;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.demo1.adapters.BlueToothListAdapter;
import com.example.demo1.bean.BlueToothData;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class BlueToothFoundReceiver extends BroadcastReceiver {
    private List<BlueToothData> addressList;
    private BlueToothListAdapter blueToothListAdapter;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.bluetooth.device.action.FOUND")){
            addressList = new ArrayDeque<>();











            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            @SuppressLint("MissingPermission")
            BlueToothData data = new BlueToothData(device.getName(),device.getAddress());
            // MAC address
            addressList.add(data);
        }
//        blueToothListAdapter = new BlueToothListAdapter(addressList);
//        if (blueToothListAdapter != null){
//            blueToothListAdapter.notifyDataSetChanged();
//        }
        System.out.println("addressList:"+addressList);
    }
}
