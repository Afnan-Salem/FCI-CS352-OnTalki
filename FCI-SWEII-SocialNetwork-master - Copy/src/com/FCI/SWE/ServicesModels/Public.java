package com.FCI.SWE.ServicesModels;


import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.PageEntity;
import com.FCI.SWE.ServicesModels.Post;
import com.FCI.SWE.ServicesModels.Privacy;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.UserPost;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;



import com.google.appengine.api.datastore.Transaction;

import org.glassfish.jersey.server.mvc.Viewable;

public class Public extends Privacy
{
	public void savePost(String timeline,String content , String feeling ,
			String type)
	{
		System.out.println("save post  f public");
		System.out.println(timeline);
		System.out.println(content);
		System.out.println(feeling);
		System.out.println(type);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		String owner = User.getCurrentActiveUser().getEmail();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		int ID = list.size()+1;
		try {
			Entity post = new Entity("posts",ID);
			post.setProperty("owner", owner);
			post.setProperty("timeline", timeline);
			post.setProperty("content", content);
			post.setProperty("privacy", type);
			post.setProperty("feeling", feeling);
			post.setProperty("likes", 0);
			datastore.put(post);
			txn.commit();
		}finally{
			if (txn.isActive())
		        	txn.rollback();
				}
	}
	@Override
	
	public Response viewprivacy(String timeline, String content,
			String feeling, String type) 
	{
		Public p = new Public();
		p.savePost(timeline, content, feeling, type);
		return null;
	}
}