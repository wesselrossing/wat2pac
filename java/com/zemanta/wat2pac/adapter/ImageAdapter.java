package com.zemanta.wat2pac.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.zemanta.wat2pac.R;
import com.zemanta.wat2pac.activities.DownloadImageTask;
import com.zemanta.wat2pac.models.Item;

import java.util.List;

/**
 * Created by dusano on 16/10/15.
 */
public class ImageAdapter extends BaseAdapter {
    Context mContext;
    List<Item> items;

    public ImageAdapter(Context mContext, List<Item> items) {
        this.mContext = mContext;
        this.items = items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            new DownloadImageTask(imageView).execute(items.get(position).getImageURL());
        } else {
            imageView = (ImageView) convertView;
        }

        return imageView;
    }
}
