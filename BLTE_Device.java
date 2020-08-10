package com.trakntell.ble1;

import android.bluetooth.BluetoothDevice;

public class BLTE_Device {
    private BluetoothDevice bluetoothDevice;
    private int rssi;

    public BLTE_Device(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }
    public String getAddress(){
        return bluetoothDevice.getAddress();
    }
    public String getName(){
        return bluetoothDevice.getName();
    }
    public void setRssi(int rssi){
        this.rssi=rssi;
    }
    public int getRssi(){
        return rssi;
    }
}
