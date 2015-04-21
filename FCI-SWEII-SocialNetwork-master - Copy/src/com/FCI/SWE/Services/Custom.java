package com.FCI.SWE.Services;
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
import com.FCI.SWE.ServicesModels.HashtagEntity;
import com.FCI.SWE.ServicesModels.PageEntity;
import com.FCI.SWE.ServicesModels.Post;
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
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class Custom implements Privacy
{
	@POST
	@Path("/custom")
	public String savePost(@FormParam("email") String email,@FormParam("privacy") String privacy,
			@FormParam("post") String post,@FormParam ("feeling") String feeling,
			@FormParam ("Hashtag") String Hashtag)
	{
		System.out.println("save post  f custom");
		System.out.println(email);
		System.out.println(post);
		System.out.println(feeling);
		System.out.println(privacy);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		String owner = User.getCurrentActiveUser().getEmail();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		int ID = list.size()+1;
		try {
			Entity p = new Entity("posts",ID);
			p.setProperty("owner", owner);
			p.setProperty("timeline", email);
			p.setProperty("content", post);
			p.setProperty("Hashtag", Hashtag);
			p.setProperty("privacy", privacy);
			p.setProperty("feeling", feeling);
			p.setProperty("likes", 0);
			p.setProperty("type", "userpost");
			datastore.put(p);
			txn.commit();
		}finally{
			if (txn.isActive())
		        	txn.rollback();
				}
		HashtagEntity Hash=new HashtagEntity();
		Hash.saveHashtag(Hashtag, ID);
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}
	
}