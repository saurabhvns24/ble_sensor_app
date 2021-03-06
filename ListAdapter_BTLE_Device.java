package com.trakntell.ble1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter_BTLE_Device extends ArrayAdapter<BLTE_Device> {
    Activity activity;
    int layoutResourceID;
    ArrayList<BLTE_Device> devices;

    public ListAdapter_BTLE_Device(Activity activity, int resource, ArrayList<BLTE_Device> objects) {
        super(activity.getApplicationContext(), resource, objects);
        this.activity = activity;
        layoutResourceID = resource;
        devices = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceID, parent, false);
        }
        BLTE_Device device = devices.get(position);
        String name = device.getName();
        String address = device.getAddress();
        int rssi = device.getRssi();
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        if (name != null && name.length() > 0) {
            tv_name.setText(name);
        } else {
            tv_name.setText("No Name");
        }
        TextView tv_rssi = (TextView) convertView.findViewById(R.id.tv_rssi);
        tv_rssi.setText("RSSI" + Integer.toString(rssi));
        TextView tv_macaddr = (TextView) convertView.findViewById(R.id.tv_macaddr);
        if (address != null && address.length() > 0) {
            tv_macaddr.setText(device.getAddress());
        } else {
            tv_macaddr.setText("No Address");
        }
        return convertView;
    }
}
