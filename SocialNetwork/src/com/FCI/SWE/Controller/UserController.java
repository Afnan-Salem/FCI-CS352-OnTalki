package com.FCI.SWE.Controller;

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
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

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
@Produces("text/html")
public class UserController {
	
	/** 
	 *  Action function to response to search request.
	 *  This function will act as a controller part, 
	 *  it will calls search service to get user from datastore 
	 *  @param uname 
	 *  @return Add page 
	 */
	@POST
	@Path("/doSearch")
	public Response usersList(@FormParam("uname") String uname)
	{
		String serviceUrl = "http://localhost:8888/rest/SearchService";
		String urlParameters = "uname=" + uname;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
		"application/x-www-form-urlencoded;charset=UTF-8");
		Vector<Object>users=new Vector<Object>();
		Map<String, Vector<Object>> map = new HashMap<String, Vector<Object>>();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONArray array = (JSONArray)obj;
		for(int i=0;i<array.size();i++)
		{
			String s1 = array.get(i).toString() ;
			obj = parser.parse(s1);
			JSONObject object = (JSONObject) obj;
			//object.put("email", );
			User returnuser=new User(object.get("email").toString()
					,object.get("name").toString());
			users.add(returnuser);
			
		}
		/*map.put("email",object.get("user_email").toString());
		map.put("name",object.get("user_name").toString());*/
		map.put("userslist", users);
		if(map.size()!=0)
			return Response.ok(new Viewable("/jsp/add", map)).build();
		else
			return Response.status(Response.Status.NOT_FOUND).entity
					("Not Found" ).build();
		}catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return null;
	}
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}
	/**
	 * Action function to render Search page, this function will be executed
	 * using URL like this /rest/search
	 * 
	 * @return search page
	 */
	@GET
	@Path("/search")
	public Response search(){
		return Response.ok(new Viewable("/jsp/search")).build();
	}
	@GET
	@Path("/writeMessage")
	public Response writeMessage(){
		return Response.ok(new Viewable("/jsp/newMessage")).build();
	}
	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/groupChat")
	public Response createGroupChat()
	{
		return Response.ok(new Viewable("/jsp/Chat")).build();
	}
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) 
	{
		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String urlParameters = "uname=" + uname + "&password=" + pass;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/LoginService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;
	}
	/** 
	 * Action function to response to add friend request. 
	 * This function will act as a * controller part, 
	 * it will calls addfriend service to add friend request in datastore 
	 * @param email 
	 * @return jsonobject 
	 */
	@POST
	@Path("/addfreind")
	public String  addFriend(@FormParam("email") String email)
	{
		String urlParameters = "email="+email;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/addferindservice", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			return object.toString();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/** 
	 * Action function this function put active user equals to null 
	 * to change active user 
	 * @return entrypoint page 
	 */
	@POST
	@Path("/Signout")
	public Response signOut()
	{
		User user=new User();
		if(user.Logout()==null)
			return Response.ok(new Viewable("/jsp/entryPoint")).build();
		return null;
	}
	/** 
	 * Action function to response to list friend request. 
	 * This function will act as a controller part, it will get users 
	 * which sent requests to current user from datastore 
	 * @return requests page 
	 */
	
	/*@POST
	@Path("/ListRequests")
	public Response ListRequests()
		{
		//Vector<String> Msenders=new Vector<String>();
		String urlParameters = User.getCurrentActiveUser().getEmail();
		String retJson = Connection.connect(
		"http://localhost:8888/rest/requests", urlParameters,
		"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		JSONParser parser1 = new JSONParser();
		Object obj,obj1;
		String retJson1=Connection.connect(
				"http://localhost:8888/rest/messages", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			//JSONArray array = (JSONArray)obj;
		//	Map<String, Vector<String>> map = new HashMap<String, Vector<String>>();
			/*for(int i=0;i<array.size();i++)
			{
				String s1 = array.get(i).toString() ;
				obj = parser.parse(s1);
				JSONObject object = (JSONObject) obj;
				//object.put("email", );
			//obj1=object;
				Msenders.add(object.get("sender").toString());
				
			}
			for(int i=0;i<Msenders.size();i++){System.out.println(Msenders.get(i));}
			map.put("requests", Msenders);
			return Response.ok(new Viewable("/jsp/messages", map)).build();*/
	/*System.out.print(object);
			obj1 = parser1.parse(retJson1);
			JSONObject object1 = (JSONObject) obj1;
		 	System.out.print(object1);
		 	if(object1.get("sender")!=null&&object.get("sender")!=null){
		 			Map<String, String> map = new HashMap<String, String>();
		 			Map<String, String> map1 = new HashMap<String, String>();
		 			map.put("email",object.get("sender").toString());
		 			map1.put("Memail",object1.get("sender").toString());
				    	map.put(null, null);
				    	map.putAll(map1);
				    	map.put("email",object.get("sender").toString());
				    	map.put("Memail",object1.get("sender").toString());
						return Response.ok(new Viewable("/jsp/notifications", map)).build();	 	
		 		}
		 	else if(object1.get("sender")!=null&&object.get("sender")==null){
		 		Map<String, String> map1 = new HashMap<String, String>();
		 		map1.put("Memail",object1.get("sender").toString());
		 		return Response.ok(new Viewable("/jsp/messages", map1)).build();
		 	}
		 	else if(object.get("sender")!=null&&object1.get("sender")==null){
		 		Map<String, String> map = new HashMap<String, String>();
		 		map.put("email",object.get("sender").toString());
		 		return Response.ok(new Viewable("/jsp/requests", map)).build();
		 	}
		 	else 
				return Response.status(Response.Status.NOT_FOUND).entity
				("No Notifications " ).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/

	/** 
	 * Action function to response to friends. 
	 * This function will act as a controller part, 
	 * it will change status of two users to friend or remain pind 
	 * @param RecieverEmail 
	 * @return jsonobject 
	 */
	@POST
	@Path("/accept")
	public String acceptRequest(@FormParam("email") String RecieverEmail)
	{
		String sender=User.getCurrentActiveUser().getEmail();
		String urlParameters =  "email=" + RecieverEmail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/friends", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			return object.toString();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	@POST
	@Path("/sendMessage")
	public String  sendMessage(@FormParam("Memail") String email,@FormParam("message") 
	String content)
	{
		String urlParameters = "Memail=" + email + "&message=" + content;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/sendmessageService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			return object.toString();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	@POST
	@Path("/sendConvMessage")
	public String  sendConvMessage(@FormParam("Memail") int ConvID,@FormParam("message") 
	String content)
	{
		String urlParameters = "Memail=" + ConvID + "&message=" + content;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/sendConvMessageService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			return object.toString();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@POST
	@Path("/saveMessage")
	//@Produces(MediaType.TEXT_PLAIN)
	public Response saveMessage(@FormParam("name") String name) 
	{
		String serviceUrl = "http://localhost:8888/rest/gmessages";
		String urlParameters = "name=" + name;
		System.out.println(name);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		Map<String, String> map = new HashMap<String, String>();
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			
	 		map.put("ConvID",object.get("ConvID").toString());
	 		map.put("Sender", User.getCurrentActiveUser().getEmail());
			if (object.get("Status").equals("OK"))
				{
			
				return Response.ok(new Viewable("/jsp/ConvInterface", map)).build();
				
				}
		} catch (ParseException e) {
			e.printStackTrace();
			/*return Response.status(Response.Status.NOT_FOUND).entity
					("No Requests " ).build();*/
		}
	 return null;
	}
	@POST
	@Path("/addFriendToConv")
	
	public Response addFriendToConv(@FormParam("ConvID") int ConvID) 
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
 		map.put("ConvID",ConvID);
 		return Response.ok(new Viewable("/jsp/addFriendToConv", map)).build();
	}
	@POST
	@Path("/addfriendtoconv")
	
	public Response addfriendtoconv(@FormParam("ConvID") int ConvID,
			@FormParam("email") String email) 
	{
		String serviceUrl = "http://localhost:8888/rest/addfriendtoconv";
		String urlParameters = "ConvID="  +ConvID +"&email="+email;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			Map<String, String> map = new HashMap<String, String>();
	 		map.put("ConvID",object.get("ConvID").toString());
	 		map.put("Sender", User.getCurrentActiveUser().getEmail());
			if (object.get("Status").equals("OK"))
			{return Response.ok(new Viewable("/jsp/ConvInterface", map)).build();}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	@POST
	@Path("/ListRequests")
	public Response ListRequests(){
		String urlParameters = User.getCurrentActiveUser().getEmail();
		String retJson = Connection.connect(
		"http://localhost:8888/rest/Notifications", urlParameters,
		"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		NotificationsController temp=null;
		try{
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			//System.out.print(object);
			if(object.get("Type").equals("MessagesNotifications"))
			{
    				temp= new MessagesController();
                    return temp.List();
                   // System.out.println("Message condition entered");
    		    }
    		else if(object.get("Type").equals("RequestsNotifications"))
    		{
				temp= new RequestsController();
               return temp.List();
                //System.out.println("Request condition entered");
		    }	
    		else 
				return Response.status(Response.Status.NOT_FOUND).entity
				("No Notifications " ).build();
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;	
	}
}