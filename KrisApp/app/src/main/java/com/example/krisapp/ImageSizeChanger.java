package com.example.krisapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageSizeChanger {

    public static Drawable resizeDrawable(Context context, int resId, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        return new BitmapDrawable(context.getResources(), resizedBitmap);
    }
}