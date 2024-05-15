package com.example.myapplication;

import android.graphics.drawable.Drawable;

import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.tileprovider.modules.IFilesystemCache;
import org.osmdroid.tileprovider.tilesource.ITileSource;

public class MyCustomTileProvider extends MapTileProviderBase {

    public MyCustomTileProvider(ITileSource it){
        super(it);

    }

    @Override
    public Drawable getMapTile(long pMapTileIndex) {
        return null;
    }

    @Override
    public int getMinimumZoomLevel() {
        return 0;
    }

    @Override
    public int getMaximumZoomLevel() {
        return 0;
    }

    @Override
    public IFilesystemCache getTileWriter() {
        return null;
    }

    @Override
    public long getQueueSize() {
        return 0;
    }
}
