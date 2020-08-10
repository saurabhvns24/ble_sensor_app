package com.trakntell.ble1;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {
    public static boolean checkBluetooth(BluetoothAdapter bluetoothAdapter){
        //Ensure Bluetooth is available on the device and it is enabled. If not,
        //display a dialog requesting user permission to enabled Bluetooth.
        if (bluetoothAdapter==null || bluetoothAdapter.isEnabled()){
            return  false;
        }
        else {
            return true;
        }
    }
    public static void requestUserBluetooth(Activity activity){
        Intent enableIntent =new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableIntent,MainActivity.REQUEST_ENABLE_BT);
    }
    public static void toast(Context context,String string){
        Toast toast=Toast.makeText(context,string,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.BOTTOM,0,0);
        toast.show();
    }
}
