package com.FCI.SWE.ServicesModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class newsFeed extends TimeLine
{

	public newsFeed(){}
	@Override
	public String getPosts() 
	{
		System.out.println("gdetposts");
		String email = User.getCurrentActiveUser().getEmail();
		System.out.println("email = "+email);	
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		newsFeed t = new newsFeed();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		JSONArray results = new JSONArray();
		for (Entity entity : pq.asIterable()) 
		{
			System.out.println("foooor");	
			JSONObject info = new JSONObject();
			if(entity.getProperty("privacy").toString().equals("PublicController")&&
					!entity.getProperty("timeline").equals(email))
			{
				System.out.println("public");	
				info.put("owner", entity.getProperty("owner"));
				info.put("post",entity.getProperty("content"));
				info.put("key", entity.getKey().getId());
				info.put("Likes", entity.getProperty("likes"));
				System.out.println("post "+entity.getProperty("content"));
				System.out.println(entity.getKey().getId());
				results.add(info);
			}
			else if (entity.getProperty("privacy").toString().equals("PrivateController")&&
					!entity.getProperty("timeline").equals(email))
			{
				System.out.println("private");	
				String name = t.getFriend();
				System.out.println("name = "+name);	
				if(entity.getProperty("timeline").equals(name))
				{
					info.put("owner", entity.getProperty("owner"));
					info.put("post",entity.getProperty("content"));
					info.put("key", entity.getKey().getId());
					info.put("Likes", entity.getProperty("likes"));
					System.out.println("post "+entity.getProperty("content"));
					System.out.println(entity.getKey().getId());
					results.add(info);
				}
			}
			else if (entity.getProperty("privacy").toString().equals("CustomController")&&
					!entity.getProperty("timeline").equals(email))
			{
				System.out.println("private");	
				String ID = String.valueOf(entity.getKey().getId());
				boolean b = t.getCustom(ID);
				if(b)
				{
					info.put("owner", entity.getProperty("owner"));
					info.put("post",entity.getProperty("content"));
					info.put("key", entity.getKey().getId());
					info.put("Likes", entity.getProperty("likes"));
					System.out.println("post "+entity.getProperty("content"));
					System.out.println(entity.getKey().getId());
					results.add(info);
				}
			}
			System.out.println("eeeeend");	
		}
		return results.toString();
		
	}
	public String getFriend()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		String email = User.getCurrentActiveUser().getEmail();
		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		JSONArray results = new JSONArray();
		for (Entity entity : pq.asIterable()) 
		{
			JSONObject info = new JSONObject();
			if(entity.getProperty("statuse").equals("friend")&&
					entity.getProperty("sender").equals(email))
			{
				return entity.getProperty("receiver").toString();
			}
			else if (entity.getProperty("statuse").equals("friend")&&
					entity.getProperty("receiver").equals(email))
			{
				return entity.getProperty("sender").toString();
			}
		}
		return null;
	}
	public boolean getCustom(String ID)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		String email = User.getCurrentActiveUser().getEmail();
		Query gaeQuery = new Query("customs");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		JSONArray results = new JSONArray();
		for (Entity entity : pq.asIterable()) 
		{
			if(entity.getProperty("postID").equals(ID)&&
					entity.getProperty("email").equals(email))
			{
				return true;
			}
		}
		return false;
	}
}
