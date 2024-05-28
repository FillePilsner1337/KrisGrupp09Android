package com.example.krisapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

public class ShelterOverlay extends ItemizedIconOverlay<OverlayItem> {

    private Context context;

    public ShelterOverlay(List<OverlayItem> shelterItems, Context context) {
        super(shelterItems, new OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                if (item instanceof ShelterWaypoint) {
                    ShelterWaypoint shelter = (ShelterWaypoint) item;
                    String message = "Adress: " + shelter.getAddress() +
                            "\nID: " + shelter.getIdNumber() +
                            "\nKapacitet: " + shelter.getCapacity();

                    LayoutInflater inflater = LayoutInflater.from(context);
                    View layout = inflater.inflate(R.layout.custom_toast, null);
                    TextView textView = layout.findViewById(R.id.toast_text);
                    textView.setText(message);

                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
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