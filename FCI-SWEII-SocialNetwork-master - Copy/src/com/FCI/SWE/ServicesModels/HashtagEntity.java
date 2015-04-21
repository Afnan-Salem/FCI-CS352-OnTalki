package com.FCI.SWE.ServicesModels;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class HashtagEntity {
	public boolean saveHashtag(String Hashtag,int PostID) 
	{
		boolean found=false;
		long HashtagID = 0;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		JSONArray results = new JSONArray();
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("Hashtagname").toString().equals(Hashtag)) 
			{
				HashtagID=entity.getKey().getId();
				String Counter = entity.getProperty("Counter").toString();
				entity.setProperty("Counter", 
						Integer.parseInt(entity.getProperty("Counter").toString())+1);	
				datastore.put(entity);
				found=true;
			}
		}
		if (found==false){
			Transaction txn = datastore.beginTransaction();
			Query gaeQuery1 = new Query("Hashtag");
			PreparedQuery pq1 = datastore.prepare(gaeQuery1);
			List<Entity> list = pq1.asList(FetchOptions.Builder.withDefaults());
			System.out.println("Size = " + list.size());
HashtagID= list.size() + 1;
			try {
			Entity hashtag = new Entity("Hashtag", list.size() + 1);

			hashtag.setProperty("Hashtagname", Hashtag);
			hashtag.setProperty("Counter", 1);
			
			
			datastore.put(hashtag);
			txn.commit();
			}finally{
				if (txn.isActive()) {
			        txn.rollback();
			    }
			}
			
		}
		saveRelation(HashtagID, PostID);
		return true;
	}
	public boolean saveRelation(long HashtagID,int postID) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("HashtagRelation");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Transaction txn = datastore.beginTransaction();
		
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		Entity hashtag = new Entity("HashtagRelation", list.size() + 1);

		hashtag.setProperty("HashtagID", HashtagID);
		hashtag.setProperty("PostID", postID);
		
		
		datastore.put(hashtag);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
		
	}
	
}
