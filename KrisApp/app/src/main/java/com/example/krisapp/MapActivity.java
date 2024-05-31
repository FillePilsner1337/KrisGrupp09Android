package com.example.krisapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Aktivitet för att visa en karta med skyddsrum.
 *
 * Till stor del är nedan kod en modifierad version med original i denna repon:
 * https://github.com/Eimg851/AndroidApp2018
 * Samt denna videon:
 * https://youtu.be/xoFtgcOoO1I
 *
 * @Author Filip Claesson, Martin Frick
 */
public class MapActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private ArrayList<ShelterObject> shelterObjects = new ArrayList<>();

    /**
     * Skapar aktiviteten och sätter upp användargränssnittet.
     * @Author Filip Claesson, Martin Frick
     */

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.getController().setZoom(12.0);
        map.getController().setCenter(new GeoPoint(55.3558, 13.0003));

        copyFileFromAssets();
        loadShelter();
        addSheltersToMap();
    }

    /**
     * Kopierar CSV-filen från tillgångar till appens filkatalog.
     * @Author Filip Claesson
     */
    private void copyFileFromAssets() {
        AssetManager assetManager = getAssets();
        try (InputStream is = assetManager.open("SR.csv");
             OutputStream os = new FileOutputStream(new File(getFilesDir(), "SR.csv"))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Läser skyddsrumsdata från CSV-filen och laddar dem till en lista med shelter-objekt.
     * @Author Filip Claesson
     */
    private void loadShelter() {
        File file = new File(getFilesDir(), "SR.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                GeoPoint geoPoint = new GeoPoint(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
                ShelterObject shelter = new ShelterObject(geoPoint, parts[2], parts[3], parts[4]);
                shelterObjects.add(shelter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lägger till skyddsrum som markörer på kartan.
     * @Author Filip Claesson
     */
    private void addSheltersToMap() {
        Drawable markerIcon = ImageSizeChanger.resizeDrawable(this, R.drawable.ic_shelter_marker, 50, 50); // Resize the marker icon to 50x50 pixels
        List<OverlayItem> overlayItems = new ArrayList<>();
        for (ShelterObject shelter : shelterObjects) {
            OverlayItem overlayItem = new ShelterWaypoint(
                    shelter.getAddress(),
                    "ID: " + shelter.getIdNumber() + "\nKapacitet: " + shelter.getCapacity(),
                    shelter.getPosition(),
                    shelter.getAddress(),
                    shelter.getIdNumber(),
                    shelter.getCapacity(),
                    markerIcon
            );
            overlayItems.add(overlayItem);
        }

        ShelterOverlay shelterOverlay = new ShelterOverlay(overlayItems, this);
        map.getOverlays().add(shelterOverlay);
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

    /**
     * Begär nödvändiga behörigheter från användaren.
     *  *
     *  * Till stor del är nedan kod en modifierad version med original i denna repon:
     *  * https://github.com/Eimg851/AndroidApp2018
     *  * Samt denna videon:
     *  * https://youtu.be/xoFtgcOoO1I
     *
     * @Author Martin Frick
     */
    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]), REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}