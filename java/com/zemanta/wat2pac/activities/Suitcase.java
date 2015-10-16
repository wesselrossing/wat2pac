package com.zemanta.wat2pac.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.zemanta.wat2pac.R;
import com.zemanta.wat2pac.adapter.ItemsAdapter;
import com.zemanta.wat2pac.airtable.Airtable;
import com.zemanta.wat2pac.airtable.OnAirtableResponseListener;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class Suitcase extends Activity implements OnAirtableResponseListener
{
    private static final int PICK_ITEM_REQUEST = 1234;

    private String id;
	private Button addItems;
	private GridView gridView;
	
	protected void onResume()
	{
		super.onResume();
		
		id = getSharedPreferences("wat2pac", 0).getString("packListId", null);
		
		setContentView(R.layout.suitcase_layout);
		
		gridView = (GridView) findViewById(R.id.gridView);
		
		addItems = (Button) findViewById(R.id.addItems);
		addItems.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view) {
                startActivityForResult(new Intent(Suitcase.this, Selector.class), PICK_ITEM_REQUEST);
			}
		});
		
		Airtable.getInstance().get("Pack%20list/" + id, this);
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_ITEM_REQUEST:
                if (resultCode == RESULT_OK) {
                    Log.i("Wat2pac", data.getStringExtra("itemID"));
                }
                break;
        }
    }
	
	@Override
	public void onAirtableResponse(String response)
	{
		Toast.makeText(this, response, Toast.LENGTH_LONG).show();
		
		try
		{
			gridView.setAdapter(new ItemsAdapter(new JSONObject(response).getJSONObject("fields").getJSONArray("What?")));
		}
		catch(JSONException e)
		{
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onError(String error)
	{
		Toast.makeText(this, error, Toast.LENGTH_LONG).show();
	}
}