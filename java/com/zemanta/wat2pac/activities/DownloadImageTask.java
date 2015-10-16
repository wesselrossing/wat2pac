package com.zemanta.wat2pac.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by dusano on 16/10/15.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private Bitmap image;

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
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
            imageView.setImageBitmap(result);
        }
    }
}
