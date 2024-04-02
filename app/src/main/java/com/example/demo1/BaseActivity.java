package com.example.demo1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.demo1.BroadcastReceiver.BluetoothStateReceiver;
import com.example.demo1.controller.BlueToothController;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseActivity  extends AppCompatActivity {
    private static final int REQ_PERMISSION_CODE = 1;
    // 蓝牙权限列表
    public ArrayList<String> requestList = new ArrayList<>();
    private BluetoothStateReceiver bluetoothStateReceiver;
    private BlueToothController blueToothController;
    private Toast toast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否支持蓝牙
        if (!blueToothController.getInstance().isBlueToothSupport()){
            showToast("当前设备不支持蓝牙");
        }
        //注册蓝牙开关监听广播
        bluetoothStateReceiver = new BluetoothStateReceiver();
        registerReceiver(bluetoothStateReceiver,new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothStateReceiver);
    }


    protected void showToast(String msg){
        if (msg ==null || msg.trim().isEmpty()){
            return;
        }
        String safeMsg = msg.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        try {
            if(toast == null){
                toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
            }else{
                toast.setText(msg);
            }
            toast.show();
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "显示消息时发生错误", e);
        }

    }


}
