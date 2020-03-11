package com.sun_asterisk.moviedb_50.utils;

import android.graphics.Bitmap;

public interface OnFetchImageListener {
    void onImageLoaded(Bitmap bitmap);
    void onImageError(Exception e);
}
