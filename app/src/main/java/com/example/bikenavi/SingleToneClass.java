package com.example.bikenavi;

import android.bluetooth.BluetoothSocket;

public class SingleToneClass {
    BluetoothSocket s;
    private static final SingleToneClass ourInstance = new SingleToneClass();
    public static SingleToneClass getInstance() {
        return ourInstance;
    }
    private SingleToneClass() {
    }
    public void setData(BluetoothSocket s) {
        this.s = s;
    }
    public BluetoothSocket getData() {
        return s;
    }
}