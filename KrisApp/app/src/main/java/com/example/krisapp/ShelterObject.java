package com.example.krisapp;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;
public class ShelterObject implements Serializable {

    private GeoPoint position;
    private String address;
    private String idNumber;
    private String capacity;

    public ShelterObject(GeoPoint position, String address, String idNumber, String capacity) {
        this.position = position;
        this.address = address;
        this.idNumber = idNumber;
        this.capacity = capacity;
    }

    public GeoPoint getPosition() {
        return position;
    }

    public String getAddress() {
        return address;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getCapacity() {
        return capacity;
    }
}