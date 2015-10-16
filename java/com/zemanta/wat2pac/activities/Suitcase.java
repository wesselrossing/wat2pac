package com.zemanta.wat2pac.activities;

import com.zemanta.wat2pac.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Suitcase extends Activity
{
	private Button addItems;
	
	protected void onCreate(android.os.Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.suitcase_layout);
		
		addItems = (Button) findViewById(R.id.addItems);
		addItems.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				startActivity(new Intent(Suitcase.this, Selector.class));
			}
		});
	}
}