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

import com.FCI.SWE.Controller.MessagesNotifications;
import com.FCI.SWE.Controller.NotificationsController;
import com.FCI.SWE.Controller.RequestsNotifications;
import com.FCI.SWE.Models.Message;
import com.FCI.SWE.ServicesModels.Custom;
import com.FCI.SWE.ServicesModels.PageEntity;
import com.FCI.SWE.ServicesModels.Post;
import com.FCI.SWE.ServicesModels.Profile;
import com.FCI.SWE.ServicesModels.TimeLine;
import com.FCI.SWE.ServicesModels.UserPost;
import com.FCI.SWE.ServicesModels.newsFeed;
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
	/*@POST
	@Path("/SearchHashtagService")
	public String SearchHashtagService(@FormParam("uname") String uname)
	{
		List<Post> list=new ArrayList<Post>();
		list=UserEntity.SearchHashtagResults(uname);
		JSONArray results = new JSONArray();
		
		for (int i=0;i<list.size();i++)
		{
			 JSONObject info = new JSONObject();
			 System.out.println(list.get(i).getownerName());
		      info.put("PostownerEmail", list.get(i).getOwner());
		      info.put("PostownerName", list.get(i).getownerName());
		      
		      info.put("Postconteent", list.get(i).getContent());
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
	@POST
	@Path("/Notify")
	public String haveNotifications () {
		String email =User.getCurrentActiveUser().getEmail();
		String typename=null,sender=null,receiver=null;//mssg=null;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		//NotificationsController temp=null;
		for (Entity entity : pq.asIterable()){
			if(entity.getProperty("receiver").toString().equals(email)){
			typename=entity.getProperty("Type").toString();
			sender=entity.getProperty("sender").toString();
			receiver=entity.getProperty("receiver").toString();
			//mssg=entity.getProperty("Message").toString();
			}
    			
		}
		JSONObject obj=new JSONObject();
		obj.put("sender",sender);
		obj.put("receiver", receiver);
		obj.put("Type", typename);
		//obj.put("Message", mssg);
		return obj.toString();
	}
//}
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
@POST
@Path("/POST")
public String POST(@FormParam("email") String email,@FormParam("post") String post,
		@FormParam ("feeling") String feeling , @FormParam("privacy") String privacy,
		@FormParam ("Hashtag") String Hashtag) 
{
	Post p = new UserPost(email,post,feeling,privacy,Hashtag);
	p.savePost(email,post,feeling,privacy,Hashtag);
	JSONObject object = new JSONObject();
	object.put("Status", "OK");
	return object.toString();
}
@POST
@Path("/saveCustomPost")
public String saveCustomPost(@FormParam("email") String email,@FormParam("post") String post,
		@FormParam ("feeling") String feeling , @FormParam("privacy") String privacy) 
{
	Custom p = new Custom();
	p.savePost(email,post,feeling,privacy);
	JSONObject object = new JSONObject();
	object.put("Status", "OK");
	return object.toString();
}
@POST
@Path("/CUSTOM")
public String  CUSTOM(@FormParam("fEmail") String fEmail )
{
	Custom p = new Custom();
	boolean b = p.saveCustom(fEmail);
	if(b){
	JSONObject object = new JSONObject();
	object.put("Status", "OK");
	return object.toString();
	}
	else
		return "FAILED";
}
@POST
@Path("/Page")
public String page(@FormParam("name") String name,
		@FormParam("Type") String type,@FormParam ("Category") String category) 
{
	//System.out.println("ser  "+owner);
	String owner=User.getCurrentActiveUser().getEmail();
	PageEntity p= new PageEntity(owner, name,type,category);
	p.savePage();
	JSONObject object = new JSONObject();
	object.put("Status", "OK");
	return object.toString();
}
@POST
@Path("/advancedSearch")
public String advancedSearch(@FormParam("name")String name){
	Vector<PageEntity>pages = PageEntity.searchPage(name);
	JSONArray returnedJSON = new JSONArray();
	for(PageEntity page:pages){
		JSONObject obj=new JSONObject();
		obj.put("id", page.getId());
		obj.put("owner", page.getOwner());
		obj.put("name", page.getName());
		returnedJSON.add(obj);
	}
	return returnedJSON.toJSONString();
}
@POST
@Path("/likePage")
public String likePage(@FormParam("name") String pageName)
{
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("Pages");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	for (Entity entity : pq.asIterable()) 
	{
		if (entity.getProperty("name").toString().equals(pageName)
				&&entity.getProperty("likes")!=null) 
		{
			System.out.println(pageName);
			String s = entity.getProperty("likes").toString();
			//int foo =Integer.parseInt(s);
			//System.out.print(s);
			//System.out.print(foo);
			entity.setProperty("likes", Integer.parseInt(entity.getProperty("likes").toString())+1);	
			datastore.put(entity);
			String liker=User.getCurrentActiveUser().getEmail();
			PageEntity p= new PageEntity(liker,pageName);
			p.saveLikers();
			JSONObject object = new JSONObject();
			object.put("Status", "OK");
		}
	}
	JSONObject object = new JSONObject();
object.put("msg", "Now you can get the Page news feed");
return object.toString();
}


@POST
@Path("/likePost")
public String likePost(@FormParam("key") int ID)
{
	Post p  = new UserPost(ID);
	return p.likePost(ID);
}

@POST
@Path("/viewprofilee")
public String  viewprofile()
{
	System.out.println("service");
	TimeLine t = new Profile();
	return t.getPosts();
}
@POST
@Path("/admin")
public String  adminPage()
{
	String email = User.getCurrentActiveUser().getEmail();
	
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("Pages");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	JSONArray results = new JSONArray();
	for (Entity entity : pq.asIterable()) 
	{
		if (entity.getProperty("owner").toString().equals(email)) 
		{System.out.println(email);
			JSONObject info = new JSONObject();
			info.put("pageName",entity.getProperty("name"));
			info.put("pageID",entity.getKey().getId());
			results.add(info);
		}
	}
	return results.toString();
}
@POST
@Path("/viewNewsfeed")
public String  viewNewsfeed()
{
	System.out.println("service");
	TimeLine t = new newsFeed();
	return t.getPosts();
}
@POST
@Path("/SearchHashtagService")
public String SearchHashtagService(@FormParam("uname") String uname)
{System.out.println("Service");
	List<Post> list=new ArrayList<Post>();
	list=UserEntity.SearchHashtagResults(uname);
	JSONArray results = new JSONArray();
	
	for (int i=0;i<list.size();i++)
	{
		 JSONObject info = new JSONObject();
		 System.out.println(list.get(i).getOwnername());
	      info.put("PostownerEmail", list.get(i).getOwner());
	      info.put("PostownerName", list.get(i).getOwnername());
	      
	      info.put("Postconteent", list.get(i).getContent());
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

}