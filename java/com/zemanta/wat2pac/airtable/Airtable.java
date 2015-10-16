package com.zemanta.wat2pac.airtable;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Looper;

public class Airtable
{
	private static Airtable singleton;
	private static final String API_KEY = AirtableApiKey.API_KEY;
	private static final String API = "https://api.airtable.com/v0/appS5yu0UXCu8A0o6/";
	
	public static synchronized Airtable getInstance()
	{
		if(singleton == null)
		{
			singleton = new Airtable();
		}
		
		return singleton;
	}
	
	private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
	private List<Communication> pending = new ArrayList<Airtable.Communication>(); // TODO replace with a more appropriate collection
	
	private Airtable()
	{
		new Communicator().start();
	}
	
	public void get(String target, OnAirtableResponseListener listener)
	{
		synchronized(pending)
		{
			pending.add(
				new Communication(
					"GET",
					API + target,
					listener
				)
			);
			
			pending.notify();
		}
	}
	
	public void create(String target, JSONObject fields, OnAirtableResponseListener listener)
	{
		String body = null;
		
		try
		{
			JSONObject root = new JSONObject();
			root.put("fields", fields);
			body = root.toString();
		}
		catch(JSONException e)
		{
			listener.onError("Could not create JSON body");
			
			return;
		}
		
		synchronized(pending)
		{
			pending.add(
				new Communication(
					"POST",
					API + target,
					body,
					listener
				)
			);
			
			pending.notify();
		}
	}
	
	public void patch(String target, JSONObject fields, OnAirtableResponseListener listener)
	{
		String body = null;
		
		try
		{
			JSONObject root = new JSONObject();
			root.put("fields", fields);
			body = root.toString();
		}
		catch(JSONException e)
		{
			listener.onError("Could not create JSON body");
			
			return;
		}
		
		synchronized(pending)
		{
			pending.add(
				new Communication(
					"PATCH",
					API + target,
					body,
					listener
				)
			);
			
			pending.notify();
		}
	}
	
	private class Communicator extends Thread
	{
		@Override
		public void run()
		{
			while(true)
			{
				Communication poll;
				
				synchronized(pending)
				{
					if(pending.size() == 0)
					{
						try
						{
							pending.wait();
						}
						catch(InterruptedException e)
						{
							
						}
					}
					
					poll = pending.remove(0);
				}
				
				final Communication communication = poll;
				
				try
				{
					URL url = new URL(communication.url);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod(communication.method);
					connection.addRequestProperty("Authorization", "Bearer " + API_KEY);
					connection.addRequestProperty("Content-Type", "application/json");
					
					if(communication.body != null)
					{
						connection.getOutputStream().write(communication.body.getBytes());
					}
					
					InputStream inputStream = new BufferedInputStream(connection.getInputStream());
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					final StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null)
					{
						response.append(line);
					}
					
					mainThreadHandler.post(new Runnable()
					{
						@Override
						public void run()
						{
							communication.listener.onAirtableResponse(response.toString());
						}
					});
				}
				catch(final Exception e) // TODO improve exception handling
				{
					mainThreadHandler.post(new Runnable()
					{
						@Override
						public void run()
						{
							communication.listener.onError(e.getClass().getSimpleName() + ", " + e.getMessage());
						}
					});
				}
			}
		}
	}
	
	private class Communication
	{
		private String method;
		private String url;
		private String body;
		private OnAirtableResponseListener listener;
		
		public Communication(String method, String url, String body, OnAirtableResponseListener listener)
		{
			this(method, url, listener);
			this.body = body;
		}
		
		public Communication(String method, String url, OnAirtableResponseListener listener)
		{
			this.method = method;
			this.url = url;
			this.listener = listener;
		}
	}
}