package com.example.krisapp;

import android.graphics.drawable.Drawable;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

/**
 * Klass som används för att rita ut skyddsrummen på kartan.
 * @Author Ola Persson, Jonatan Tempel, Filip Claesson
 */
public class ShelterWaypoint extends OverlayItem {

    private String address;
    private String idNumber;
    private String capacity;

    public ShelterWaypoint(String title, String snippet, GeoPoint geoPoint, String address, String idNumber, String capacity, Drawable marker) {
        super(title, snippet, geoPoint);
        this.address = address;
        this.idNumber = idNumber;
        this.capacity = capacity;
        setMarker(marker); // Såhär sätter man en anpassad ikon
    }

    public GeoPoint getGeoPoint() {
        return (GeoPoint) getPoint();
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