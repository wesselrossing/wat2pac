package com.zemanta.wat2pac.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.zemanta.wat2pac.adapter.ImageAdapter;

import java.io.InputStream;
import java.util.List;

/**
 * Created by dusano on 16/10/15.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageAdapter imageAdapter;
    private List<Bitmap> bitmaps;
    private Bitmap image;
    private int index;

    public DownloadImageTask(ImageAdapter imageAdapter, List<Bitmap> bitmaps, int index) {
        this.imageAdapter = imageAdapter;
        this.bitmaps = bitmaps;
        this.index = index;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            image = null;
        }
        return image;
    }

    @SuppressLint("NewApi")
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            bitmaps.set(index, result);
            imageAdapter.notifyDataSetChanged();
        }
    }
}
