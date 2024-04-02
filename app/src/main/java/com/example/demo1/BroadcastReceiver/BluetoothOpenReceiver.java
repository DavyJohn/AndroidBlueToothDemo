package com.example.demo1.BroadcastReceiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothOpenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("openblue")){
            intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            //设置为一直开启 0 为永久
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            context.startActivity(intent);
        }

    }
}
