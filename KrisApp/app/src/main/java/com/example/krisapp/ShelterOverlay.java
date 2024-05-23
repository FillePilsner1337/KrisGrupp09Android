package com.example.krisapp;

import android.content.Context;
import android.widget.Toast;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

public class ShelterOverlay extends ItemizedIconOverlay<OverlayItem> {

    private Context context;

    //Metoderna måste heta så konstigt pga man ärver dem från OnItemGestureListener
    public ShelterOverlay(List<OverlayItem> shelter, Context context) {
        super(shelter, new OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                if (item instanceof ShelterWaypoint) {
                    ShelterWaypoint shelter = (ShelterWaypoint) item;
                    String message = "Address: " + shelter.getAddress() +
                            "\nID: " + shelter.getIdNumber() +
                            "\nCapacity: " + shelter.getCapacity();
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, context);
        this.context = context;
    }
}