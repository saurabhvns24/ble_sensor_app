package com.trakntell.ble1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_ENABLE_BT = 1;
    private HashMap<String, BLTE_Device> mBTDeviceHashMap;
    private ArrayList<BLTE_Device> mBTDeviceArrayList;
    private ListAdapter_BTLE_Device adapter;
    private Button btn_Scan;
    private BraodCastReceiver_BTState mBtStateUpdateReceiver;
    private Scanner_BTLE mBTLeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Use this check to determine whether BLE is supported on the device . Then
        //you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE not supported");
            finish();
        }
        mBtStateUpdateReceiver = new BraodCastReceiver_BTState(getApplicationContext());
        mBTLeScanner = new Scanner_BTLE(this, 7500, -100);

        mBTDeviceHashMap = new HashMap<>();
        mBTDeviceArrayList = new ArrayList<>();
        adapter = new ListAdapter_BTLE_Device(this, R.layout.btle_device_list_item, mBTDeviceArrayList);
        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        ((ScrollView) findViewById(R.id.scrollView)).addView(listView);
        btn_Scan = (Button) findViewById(R.id.btn_scan);
        findViewById(R.id.btn_scan).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBtStateUpdateReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScan();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBtStateUpdateReceiver);
        stopScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check which request we are responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Utils.toast(getApplicationContext(),"Thank you turning on Bluetooth");
            } else if (resultCode == RESULT_CANCELED) {
                Utils.toast(getApplicationContext(), "Please turn on Bluetooth");
            }
        }
    }
    /*
    Called when an item in the ListView is clicked
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //used in another
    }
    /*
    Called when scan button is clicked.
    @param v The view that was clicked
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                Utils.toast(getApplicationContext(), "Scan Button Pressed");
                if (!mBTLeScanner.isScanning()){
                 startScan();
                }else {
                    stopScan();
                }

                break;
            default:
                break;
        }
    }

    public void addDevice(BluetoothDevice device, int new_rssi) {
        String address = device.getAddress();
        if (!mBTDeviceHashMap.containsKey(address)) {
            BLTE_Device blte_device = new BLTE_Device(device);
            blte_device.setRssi(new_rssi);
            mBTDeviceHashMap.put(address, blte_device);
            mBTDeviceArrayList.add(blte_device);
        } else {
            mBTDeviceHashMap.get(address).setRssi(new_rssi);
        }
        adapter.notifyDataSetChanged();
    }
    public void startScan(){
        btn_Scan.setText("Scanning.....");
        mBTDeviceArrayList.clear();
        mBTDeviceHashMap.clear();
        adapter.notifyDataSetChanged();
        mBTLeScanner.start();
    }

    public void stopScan() {
        btn_Scan.setText("Scan Again");
        mBTLeScanner.stop();
    }
}
