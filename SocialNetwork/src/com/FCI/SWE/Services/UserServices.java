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

import com.FCI.SWE.Controller.MessagesController;
import com.FCI.SWE.Controller.NotificationsController;
import com.FCI.SWE.Controller.RequestsController;
import com.FCI.SWE.Models.Message;
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
/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
	/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();

	}
	/** 
	 * Search Service, this service will be called to make search process 
	 * also will check user data and returns new user from datastore 
	 * @param uname 
	 * @return jsonobject
	 */
	@POST
	@Path("/SearchService")
	public String SearchService(@FormParam("uname") String uname)
	{
		JSONArray results = new JSONArray();
		List<UserEntity> user = UserEntity.SearchResults(uname);
		for (int i=0;i<user.size();i++)
		{
			 JSONObject info = new JSONObject();
			 //info.putAll((Map) user.get(i));
		      info.put("email", user.get(i).getEmail());
		      info.put("name", user.get(i).getName());
			results.add(info);
		}
      return results.toString();
	}
	/** 
	 * Add Friend Service, this service will be called to make send 
	 * request to any member process 
	 * @param email 
	 * @return jsonobject 
	 */
	@POST
	@Path("/addferindservice")
	public String addFerindService(@FormParam("email") String email)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		User user=User.getCurrentActiveUser();
		Entity friend = new Entity("requests", list.size() + 1);
		friend.setProperty("sender", user.getEmail());
		friend.setProperty("receiver", email);
		friend.setProperty("statuse", "pending");
		datastore.put(friend);
		UserEntity user1=new UserEntity();
		boolean Not=user1.saveNotification(user.getEmail(), email, "RequestsNotifications","pending");
		JSONObject obj=new JSONObject();
		if(Not==true )
		obj.put("msq", "request is send");
		
		return obj.toString();
	}
	/** 
	 * Request Service, this service will be called to show friend requests 
	 * @return jsonobject 
	 */
	/*@POST
	@Path("/addferindservice")
	public String addFerindService(@FormParam("email") String email)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		User user=User.getCurrentActiveUser();
		Entity notification = new Entity("Notifications", list.size() + 1);
		notification.setProperty("sender", user.getEmail());
		notification.setProperty("receiver", email);
		notification.setProperty("statuse", "pending");
		notification.setProperty("Type","RequestsNotifications");
		datastore.put(notification);
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		JSONObject obj=new JSONObject();
		obj.put("msq", "request is send");
		return obj.toString();
	}*/
	@POST
	@Path("/Notifications")
	public String haveNotifications () {
		//String email =User.getCurrentActiveUser().getEmail();
		String typename=null;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//NotificationsController temp=null;
		for (Entity entity : pq.asIterable()){
			typename=entity.getProperty("Type").toString();
    			
		}
		JSONObject obj=new JSONObject();
		obj.put("Type", typename);
		return obj.toString();}
	/**
	 * Friends Service, this service will be called to make accept request
	 *  and update data store 
	 * @param sender 
	 * @return jsonobject 
	 */
	@POST
	@Path("/friends")
	public String acceptFriend(@FormParam("email") String sender)
	{
		String RecieverEmail=User.getCurrentActiveUser().getEmail();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("requests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("receiver").toString().equals(RecieverEmail)
					&& entity.getProperty("sender").toString().equals(sender)
					&&entity.getProperty("statuse").toString().equals("pending")) 
			{
				entity.setProperty("statuse", "friend");	
				datastore.put(entity);
			}
		}
	JSONObject obj=new JSONObject();
	obj.put("msg", "Now you are freinds");
	return obj.toString();
	}
	

@POST
	@Path("/sendmessageService")
	public String  sendService(@FormParam("Memail") String email,@FormParam("message") 
	String content)
	{UserEntity user1=new UserEntity();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		User user=User.getCurrentActiveUser();
		Entity newMessage = new Entity("messages", list.size() + 1);
		newMessage.setProperty("sender", user.getEmail());
		newMessage.setProperty("receiver", email);
		newMessage.setProperty("Message",content);
		newMessage.setProperty("statuse", "unread");
		datastore.put(newMessage);
		boolean Not=user1.saveNotification(user.getEmail(), email, "MessagesNotifications","unread");
		JSONObject obj=new JSONObject();
		obj.put("msg", "message is sent");
		return obj.toString();
		}
	/*@POST
	@Path("/sendmessageService")
	public String  sendService(@FormParam("Memail") String email,@FormParam("message") 
	String content)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		User user=User.getCurrentActiveUser();
		Entity notification = new Entity("Notifications", list.size() + 1);
		notification.setProperty("sender", user.getEmail());
		notification.setProperty("receiver", email);
		notification.setProperty("Message",content);
		notification.setProperty("statuse", "unread");
		notification.setProperty("Type","MessagesNotifications");
		datastore.put(notification);

		JSONObject obj=new JSONObject();
		obj.put("msg", notification.getProperty("receiver"));
		return obj.toString();
		}*/
@POST
@Path("/gmessages")
public String messages(@FormParam("name") String name) {
	Message msg = new Message(name);
	int ConvID=msg.saveMessage(name);
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("Receiver");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
	User user=User.getCurrentActiveUser();
	Entity newreceiver = new Entity("Receiver", list.size() + 1);
	newreceiver.setProperty("CONID", ConvID);
	newreceiver.setProperty("receiver", user.getEmail());
	datastore.put(newreceiver);
	JSONObject object = new JSONObject();
	object.put("Status", "OK");
	object.put("ConvID", ConvID);
	
	return object.toString();
}
@POST
@Path("/addfriendtoconv")
public String  addfriendtoconv(@FormParam("ConvID") int ConvID,
		@FormParam("email") String email)
{
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("Receiver");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
	User user=User.getCurrentActiveUser();
	Entity newreceiver = new Entity("Receiver", list.size() + 1);
	newreceiver.setProperty("CONID", ConvID);
	newreceiver.setProperty("receiver", email);
	
	datastore.put(newreceiver);
	JSONObject obj=new JSONObject();
	obj.put("Status", "OK");
	obj.put("ConvID", ConvID);
	return obj.toString();
	}
@POST
@Path("/sendConvMessageService")
public String  sendConvMessageService(@FormParam("Memail") String ConvID,@FormParam("message") 
String content)
{
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("messages");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
	User user=User.getCurrentActiveUser();
	Entity newMessage = new Entity("messages", list.size() + 1);
	newMessage.setProperty("sender", user.getEmail());
	newMessage.setProperty("receiver", ConvID);
	newMessage.setProperty("Message",content);
	newMessage.setProperty("statuse", "unread");
	datastore.put(newMessage);
	Message M=new Message();
	M.searchReceivers(ConvID);
	M.notifyReceiver(ConvID);
	JSONObject obj=new JSONObject();
	obj.put("msg", "message is sent");
	return obj.toString();
	}
}