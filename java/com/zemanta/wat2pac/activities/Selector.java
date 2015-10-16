package com.zemanta.wat2pac.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.zemanta.wat2pac.R;
import com.zemanta.wat2pac.airtable.Airtable;
import com.zemanta.wat2pac.airtable.OnAirtableResponseListener;
import com.zemanta.wat2pac.adapter.ImageAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

public class Selector extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

        super.onCreate(savedInstanceState);

        Log.i("Wat2Pac", "Setting up selector...");
        setContentView(R.layout.selector_layout);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(Selector.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


        Airtable.getInstance().get("Items?limit=100&view=Main%20View", new OnAirtableResponseListener() {
            @Override
            public void onAirtableResponse(final String response) {
                try {
                    Log.i("Wat2Pac", response);
                    JSONObject airtableResponse = new JSONObject(response);
                    JSONArray arr = airtableResponse.getJSONArray("records");
                    for (int i = 0; i < arr.length(); i++) {
                        Log.i("Wat2Pac", arr.getJSONObject(i).toString());
                    }

                } catch(Exception e) {
                    Log.e("Wat2Pac exception", "Error accessing Airtable data", e);
                }
            }

            @Override
            public void onError(String error)
            {
                Toast.makeText(Selector.this, error, Toast.LENGTH_LONG).show();
            }
        });
	}
}