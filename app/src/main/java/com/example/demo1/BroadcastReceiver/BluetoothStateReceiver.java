package com.example.demo1.BroadcastReceiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.demo1.BaseApplication;

import java.util.ArrayList;

public class BluetoothStateReceiver  extends BroadcastReceiver {

    private Toast toast;
    private ArrayList<String> blueList = new ArrayList<>();
    private ArrayList<String> deviceList = new ArrayList<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        //蓝牙开关的状态
        String action = intent.getAction();

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state){
                case BluetoothAdapter.STATE_ON:
                    showToast("已经开启蓝牙");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    showToast("已经关闭蓝牙");
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    showToast("开启蓝牙");
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    showToast("关闭蓝牙");
                    break;
                case BluetoothAdapter.ERROR:
                    showToast("错误");
                    break;
            }
        }

    }
    private void showToast(String msg){
        if (toast ==null){
            toast = Toast.makeText(BaseApplication.getInstance(),msg,Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}
