package com.zemanta.wat2pac.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Suitcase extends Activity
{
	protected void onCreate(android.os.Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Button button = new Button(this);
		button.setText("Add something!");
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				startActivity(new Intent(Suitcase.this, Selector.class));
			}
		});
		
		setContentView(button);
	}
}