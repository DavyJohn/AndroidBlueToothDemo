package com.example.demo1.javaApp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.demo1.BaseActivity;
import com.example.demo1.BroadcastReceiver.BlueToothFoundReceiver;
import com.example.demo1.BroadcastReceiver.BlueToothStartedReceiver;
import com.example.demo1.BroadcastReceiver.BluetoothOpenReceiver;
import com.example.demo1.R;
import com.example.demo1.adapters.BlueToothListAdapter;
import com.example.demo1.bean.BlueToothData;
import com.example.demo1.controller.BlueToothController;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kotlin.collections.ArrayDeque;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_BLUETOOTH_SCAN_PERMISSION = 100;
    private static final int REQ_PERMISSION_CODE = 200;
    private BluetoothOpenReceiver bluetoothOpenReceiver;
    private boolean isBluetoothOpenReceiverRegistered = false;

    private boolean isScnning = false;
    private BlueToothStartedReceiver blueToothStartedReceiver;
    private BlueToothFoundReceiver blueToothFoundReceiver;

    private BlueToothController blueToothController;
    private Button button;
    private BluetoothAdapter bluetoothAdapter;

    private RecyclerView recyclerView;
    private List<BlueToothData> blueToothDataList;
    private BlueToothListAdapter blueToothListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blueToothStartedReceiver = new BlueToothStartedReceiver();
        blueToothFoundReceiver = new BlueToothFoundReceiver();
        registerReceiver(blueToothStartedReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
        initView();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPermision();

    }

    private void getPermision() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)!= PackageManager.PERMISSION_GRANTED){
            requestList.add(Manifest.permission.BLUETOOTH);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN)!= PackageManager.PERMISSION_GRANTED){
            requestList.add(Manifest.permission.BLUETOOTH_ADMIN);
        }
        //由于 sdk最小为26 判断条件可以去除
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M || Build.VERSION.SDK_INT < Build.VERSION_CODES.S ){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestList.add(Manifest.permission.BLUETOOTH_SCAN);
            requestList.add(Manifest.permission.BLUETOOTH_ADVERTISE);
            requestList.add(Manifest.permission.BLUETOOTH_CONNECT);
        }
        if (!requestList.isEmpty()) {
            ActivityCompat.requestPermissions(this, requestList.toArray(new String[0]), REQ_PERMISSION_CODE);
        }

    }

    private void initView() {
        // start recyclerview
        recyclerView = findViewById(R.id.bluetoooth_list);
        blueToothDataList = new ArrayDeque<>();
        blueToothListAdapter = new BlueToothListAdapter(MainActivity.this);
        blueToothListAdapter.setData(blueToothDataList);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(blueToothListAdapter);
        blueToothListAdapter.setOnClickListener(new BlueToothListAdapter.OnItemClickListener() {
            @Override
            public void onTemClick(View view, int position, BlueToothData blueToothData) {
                showToast(blueToothData.getName()== "" ? blueToothData.getAddress() : blueToothData.getName());
            }
        });
        //end


        if (!blueToothController.getInstance().isBlueToothOpen()) {
            bluetoothOpenReceiver = new BluetoothOpenReceiver();
            registerReceiver(bluetoothOpenReceiver, new IntentFilter("openblue"));
            isBluetoothOpenReceiverRegistered = true;
        }
        button = findViewById(R.id.btn_found);
        button.setOnClickListener(this);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_found) {
            //判断蓝牙是否开启
            if (BlueToothController.getInstance().isBlueToothOpen()){
                BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();
                MyScanCallBack myScanCallBack = new MyScanCallBack();
                if (!isScnning){
                    isScnning = true;
                    scanner.startScan(myScanCallBack);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            button.setText(R.string.stop_found);
                        }
                    }).start();
                }else {
                    isScnning = false;
                    scanner.stopScan(myScanCallBack);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            button.setText(R.string.start_found);
                        }
                    }).start();
                }

//                bluetoothAdapter.startDiscovery(); //开始搜索
//                registerReceiver(blueToothFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }else {
                Intent intent = new Intent("openblue");
                sendBroadcast(intent);
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBluetoothOpenReceiverRegistered){
            unregisterReceiver(bluetoothOpenReceiver);
            isBluetoothOpenReceiverRegistered = false;
        }

//        unregisterReceiver(blueToothFoundReceiver);
        unregisterReceiver(blueToothStartedReceiver);
    }

    protected void showToast(String msg){
        super.showToast(msg);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_BLUETOOTH_SCAN_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                    sendBroadcast(intent);
                }else {
                    showToast("权限"+permissions[0]+"开启失败");
                }
                break;
            case REQ_PERMISSION_CODE:
                requestList.clear();
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Thread.sleep(0);
                        showToast("权限"+permissions[0]+"开启");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    // 用户拒绝了权限请求，可以在这里提示用户权限的重要性
                    showToast("权限"+permissions[0]+"开启失败");
                }
                break;
        }
    }


    private class MyScanCallBack extends ScanCallback {
        @SuppressLint("MissingPermission")
        public void onScanResult(int callbackType, ScanResult result)
        {
            //更新数据源但是要注意去重set
            blueToothDataList.add(new BlueToothData(result.getDevice().getName() == null ? "未知设备" : result.getDevice().getName(),result.getDevice().getAddress()));
            List<BlueToothData> list = blueToothDataList.stream().distinct().collect(Collectors.toList());
            blueToothListAdapter.setData(list);

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }

    }
}