package com.FCI.SWE.ServicesModels;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PageTimeline extends TimeLine
{
	public PageTimeline(){}
	@Override
	public String getPosts() 
	{
		//System.out.println("gdetposts");
		String email = User.getCurrentActiveUser().getEmail();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		JSONArray results = new JSONArray();
		for (Entity entity : pq.asIterable()) 
		{
			
			if (entity.getProperty("owner").equals(email)&&entity.getProperty("Type").toString().equals("Page Post")) 
			{
				JSONObject info = new JSONObject();
				info.put("post",entity.getProperty("content"));
				info.put("key", entity.getKey().getId());
				info.put("Likes", entity.getProperty("likes"));
				System.out.println(entity.getKey().getId());
				results.add(info);
			}
		}
		return results.toString();
	}
}