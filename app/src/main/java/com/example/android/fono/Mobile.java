package com.example.android.fono;


import java.io.Serializable;

public class Mobile implements Serializable{
    private String deviceName;
    private String brand;
    private String size;
    private String colors;

    public String getDeviceName() {
        return deviceName;
    }

    public Mobile setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Mobile setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getSize() {
        return size;
    }

    public Mobile setSize(String size) {
        this.size = size;
        return this;
    }

    public String getColors() {
        return colors;
    }

    public Mobile setColors(String colors) {
        this.colors = colors;
        return this;
    }
}
