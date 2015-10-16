package com.zemanta.wat2pac.activities;

import android.app.Activity;
import android.content.Intent;
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
import com.zemanta.wat2pac.models.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Selector extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

        super.onCreate(savedInstanceState);

        Log.i("Wat2Pac", "Setting up selector...");
        setContentView(R.layout.selector_layout);

        Airtable.getInstance().get("Items?limit=100&view=Main%20View", new OnAirtableResponseListener() {
            @Override
            public void onAirtableResponse(final String response) {
                try {
                    JSONObject airtableResponse = new JSONObject(response);
                    JSONArray arr = airtableResponse.getJSONArray("records");
                    final List<Item> items = new ArrayList<Item>();
                    for (int i = 0; i < arr.length(); i++) {
                        String id = arr.getJSONObject(i).getString("id");
                        JSONObject itemFields = arr.getJSONObject(i).getJSONObject("fields");
                        if (!itemFields.has("Attachments") || itemFields.getJSONArray("Attachments").length() == 0) {
                            continue;
                        }
                        JSONObject attachment = itemFields.getJSONArray("Attachments").getJSONObject(0);
                        Item item = new Item(
                                id,
                                itemFields.getString("Name"),
                                attachment.getJSONObject("thumbnails").getJSONObject("large").getString("url")
                        );
                        items.add(item);
                    }

                    GridView gridview = (GridView) findViewById(R.id.gridview);

                    gridview.setAdapter(new ImageAdapter(Selector.this, items));

                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            Intent data = new Intent();
                            data.putExtra("itemID", items.get(position).getId());
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        }
                    });

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