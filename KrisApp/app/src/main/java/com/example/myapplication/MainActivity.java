package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;

    /*private MapView mapView;
    private MapController mapController; */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
/*        setContentView(R.layout.activity_map);  // Replace with your layout resource ID

//        MyCustomTileProvider myTileProvider = new MyCustomTileProvider();
//        mapView.setTileProvider(myTileProvider);


        mapView = findViewById(R.id.mapview);

        GeoPoint startPoint = new GeoPoint(59.32944, 18.06852);
        mapView.getController().setCenter(startPoint);

        // Set zoom
        mapView.getController().setZoom(10.0);*/

        Context ctx = getApplicationContext();
        //Funkar inte utan depricated.
        //Kolla up lifetime cycles och hur det funkar.
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

        //Kodexempel castade viewn in som (MapView)
        map = findViewById(R.id.map);
        //Undersök om denna gör appen segare än nödvändigt.
        map.setTileSource(TileSourceFactory.MAPNIK);

        //Snodd permission request kod. Funkar bra.
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
                });

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        map.getController().setZoom(12.0);

        //Klurigt som fan, funkar inte perfekt. Kolla upp.
        double latitudeMicrodegrees = 55.3558 * 1000000;
        double longitudeMicrodegrees = 13.0003 * 1000000;

        //Enda som funkar är med depricated klass GeoPoint.
        map.getController().setCenter(new GeoPoint((int) latitudeMicrodegrees, (int) longitudeMicrodegrees));
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    /*public String getTileURLString(long pMapTileIndex) {
        return "http://api.agromonitoring.com/tile/1.0"
                + "/" + MapTileIndex.getZoom(pMapTileIndex)
                + "/" + MapTileIndex.getX(pMapTileIndex)
                + "/" + MapTileIndex.getY(pMapTileIndex)
                + "/1205e5afb00/5ea46b9bf6e0ca0dd970ae34?appid=abc";
    }*/
}