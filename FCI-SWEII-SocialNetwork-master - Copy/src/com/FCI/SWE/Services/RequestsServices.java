package com.FCI.SWE.Services;

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
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;






import com.google.appengine.api.datastore.Transaction;

import org.json.simple.JSONArray;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class RequestsServices implements NotificationsInterface {
	@POST
	@Path("/requests")
public String notifyUser(){
		String sender = null,type=null;
		String email=User.getCurrentActiveUser().getEmail();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("receiver").toString().equals(email)
					&& entity.getProperty("statuse").toString().equals("pending")) 
			{	
				 sender=entity.getProperty("sender").toString();
				 type=entity.getProperty("Type").toString();
			}
		}
		JSONObject obj=new JSONObject();
		obj.put("sender", sender);
		obj.put("Type", type);
		return obj.toString();
	}
	
}