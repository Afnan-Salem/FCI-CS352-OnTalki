package com.FCI.SWE.Controller;

import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

import com.FCI.SWE.ServicesModels.Post;
import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.PageEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.UserPost;
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
		if(uname.contains("@")){
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
		
		map.put("userslist", users);
		if(map.size()!=0)
			return Response.ok(new Viewable("/jsp/add", map)).build();
		else
			return Response.status(Response.Status.NOT_FOUND).entity
					("Not Found" ).build();
		}catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}}
		else if (uname.contains("#")){
			
			String serviceUrl = "http://localhost:8888/rest/SearchHashtagService";
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
			{System.out.print("HIIIIIII");
				String s1 = array.get(i).toString() ;
				obj = parser.parse(s1);
				JSONObject object = (JSONObject) obj;
				Post p=new UserPost(object.get("PostownerEmail").toString(),
						object.get("PostownerName").toString(),
						
						object.get("Postconteent").toString());		
				users.add(p);
				System.out.println(p.getOwnername());
				System.out.println(object.get("Postconteent").toString());
				//hsl7 hnaaaaaaaaaaaaaa
			
			}
			map.put("userslist", users);
			for(int i=0;i<users.size();i++){System.out.println(users.get(i).toString());}
			if(map.size()!=0)
				return Response.ok(new Viewable("/jsp/HashtagSearch", map)).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity
						("Not Found" ).build();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}}
		return null;
	}
	
	/*	else if (uname.contains("#")){
			
			String serviceUrl = "http://localhost:8888/rest/SearchHashtagService";
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
			{System.out.print("HIIIIIII");
				String s1 = array.get(i).toString() ;
				obj = parser.parse(s1);
				JSONObject object = (JSONObject) obj;
				Post p=new Post(object.get("PostownerEmail").toString(),
						object.get("PostownerName").toString(),
						
						object.get("Postconteent").toString());		
				users.add(p);
				System.out.println(p.getownerName());
				System.out.println(object.get("Postconteent").toString());
				//hsl7 hnaaaaaaaaaaaaaa
			
			}
			map.put("userslist", users);
			//for(int i=0;i<users.size();i++){System.out.println(users.get(i).toString());}
			if(map.size()!=0)
				return Response.ok(new Viewable("/jsp/HashtagSearch", map)).build();
			else
				return Response.status(Response.Status.NOT_FOUND).entity
						("Not Found" ).build();
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}}
		return null;
	}*/
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
	@GET
	@Path("/createPost")
	public Response createPost() {
		System.out.println("create post");
		return Response.ok(new Viewable("/jsp/choosePost")).build();
	}
	@GET
	@Path("/searchFriend")
	public Response searchFriend(){
		System.out.println("choose post");
		return Response.ok(new Viewable("/jsp/searchFriend")).build();
	}
	
	/*@POST
	 @Path("/post")
	 public String post(@FormParam("email") String email,@FormParam("privacy") String privacy,
	   @FormParam("post") String post,@FormParam ("feeling") String feeling) 
	 {
	  Class<?> c = null;
	  try
	  {
	   System.out.println("tryy"); 
	   c = Class.forName("com.FCI.SWE.Controller."+privacy+".Controller");
	   PrivacyControllers p = null;
	   p = (PrivacyControllers) c.newInstance();
	   p.doPrivacy(email,privacy,post,feeling);
	  }
	  catch(Exception e)
	  {
	   System.out.println("catch");
	   e.printStackTrace();
	  }
	  return null;
	 }*/
	@GET
	@Path("/cus")
	public Response cus() 
	{
		return  Response.ok(new Viewable("/jsp/entryPoint")).build();
	}
	@POST
	@Path("/custom")
	public Response custom(@FormParam("timeline") String timeline,@FormParam("content") String content,
			@FormParam("feeling") String feeling,@FormParam("type") String type,
			@FormParam("fEmail") String fEmail) 
	{	
		String serviceUrl = "http://localhost:8888/rest/CUSTOM";
		String urlParameters = "fEmail="+fEmail;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
			{
				
		 		return Response.ok(new Viewable("/jsp/chooseCustoms")).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
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
	@Path("/HashtagPosts")
	
	public Response HashtagPosts(/*@FormParam("posts") Vector posts*/){
		//for(int i=0;i<posts.size();i++){System.out.println(posts.get(i));}
		return Response.ok(new Viewable("/jsp/entrypoint")).build();
	}
	@POST
	@Path("/ListRequests")
	public Response ListRequests(){
		String urlParameters = User.getCurrentActiveUser().getEmail();
		String Email=User.getCurrentActiveUser().getEmail();
		String retJson = Connection.connect(
		"http://localhost:8888/rest/Notify", urlParameters,
		"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try{
			
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			//System.out.print(object);
			while(true)
			{
				if(object.get("receiver") == null) 
					return Response.status(Response.Status.NOT_FOUND).entity
					("No Notifications " ).build();
				AllNotifications temp=new AllNotifications();
				temp.invoke();
				System.out.print("ENNNNNNNN");
				return temp.invoke();
			}
			
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;	
	}
	
	/*@GET
	@Path("/viewprofile")
	public Response  viewprofile()
	{
		System.out.print("Hiiiiii");
		String urlParameters =  "email="+User.getCurrentActiveUser().getEmail(); 
		String retJson = Connection.connect(
				"http://localhost:8888/rest/viewprofilee", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		System.out.print("Hiiiiii");
		Vector<Object>posts=new Vector<Object>();
		Map<String, Vector<Object>> map = new HashMap<String, Vector<Object>>();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			System.out.println("tryy");
			obj = parser.parse(retJson);
			JSONArray array = (JSONArray)obj;
		for(int i=0;i<array.size();i++)
		{
			System.out.println("HIIIII");
			String s1 = array.get(i).toString() ;
			obj = parser.parse(s1);
			JSONObject object = (JSONObject) obj;
			System.out.println("content = "+object.get("content").toString());
			Post p = new UserPost(/*object.get("content").toString());
			p.setContent(object.get("content").toString());
			posts.add(p.getContent());
		}
		//for(int i=0;i<posts.size();i++){System.out.println(((Post) posts.get(i)).getContent());}
		map.put("postsList", posts);
		if(map.size()!=0)
		{
			System.out.println("eeeeeeeeeeeeeee");
			return Response.ok(new Viewable("/jsp/profile", map)).build();
		}
		else
		{
			System.out.println("tttt");
			return Response.status(Response.Status.NOT_FOUND).entity
					("Not Found" ).build();
		}
		}catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return null;
	}*/
	@GET
	@Path("/viewprofile")
	public Response  viewprofile()
	{
		String urlParameters =  "email="+User.getCurrentActiveUser().getEmail(); 
		String retJson = Connection.connect(
				"http://localhost:8888/rest/viewprofilee", urlParameters,
				"POST", "application/x-wwwc-form-urlencoded;charset=UTF-8");
		Vector<Object>posts=new Vector<Object>();
		Map<String, Vector<Object>> map = new HashMap<String, Vector<Object>>();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			System.out.println("try");
			obj = parser.parse(retJson);
			JSONArray array = (JSONArray)obj;
		for(int i=0;i<array.size();i++)
		{
			System.out.println("foooor");
			String s1 = array.get(i).toString() ;
			obj = parser.parse(s1);
			JSONObject object = (JSONObject) obj;
			int ID = Integer.parseInt(object.get("key").toString());
			int likes = Integer.parseInt(object.get("Likes").toString());
			System.out.println(ID+"  "+object.get("post").toString()+"  "+likes);
			UserPost p = new UserPost(ID,object.get("post").toString(),likes);
			posts.add(p);
		}
		for(int i = 0 ; i< posts.size();i++)
		{
		System.out.println (((Post) posts.get(i)).getID());
		System.out.println (((Post) posts.get(i)).getContent());
		}
		map.put("postsList", posts);
		if(map.size()!=0)
		{
			return Response.ok(new Viewable("/jsp/profile", map)).build();
		}
		else
		{
			return Response.status(Response.Status.NOT_FOUND).entity
					("Not Found" ).build();
		}
		}catch (ParseException e) {
		e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/Pages")
	public Response Pages() {
		return Response.ok(new Viewable("/jsp/Pages")).build();
	}
	@GET
	@Path("/createPage")
	public Response createPage() {
		return Response.ok(new Viewable("/jsp/newPage")).build();
	}
	@GET
	@Path("/searchPage")
	public Response Page() {
		return Response.ok(new Viewable("/jsp/PageSearch")).build();
	}
	@POST
	@Path("/page")
	public String page(@FormParam("email") String owner,@FormParam("name") String name,
			@FormParam("Type") String type,@FormParam ("Category") String category) 
	{System.out.println("con  "+owner);
		
		String serviceUrl = "http://localhost:8888/rest/Page";
		String urlParameters = "email=" + owner+"&name="+name+"&Type="+type+"&Category="+category;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Created Successfully";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(owner);
		System.out.println(name);
		System.out.println(type);
		System.out.println(category);
		return "Failed";
	}
	@GET
	@Path("/admin")
	public Response  adminPage()
	{
		String urlParameters =  "email="+User.getCurrentActiveUser().getEmail(); 
		String retJson = Connection.connect(
				"http://localhost:8888/rest/admin", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		System.out.print("Hiiiiii");
		Vector<Object>pages=new Vector<Object>();
		Map<String, Vector<Object>> map = new HashMap<String, Vector<Object>>();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONArray array = (JSONArray)obj;
		for(int i=0;i<array.size();i++)
		{System.out.println("HIIIII");
			String s1 = array.get(i).toString() ;
			obj = parser.parse(s1);
			JSONObject object = (JSONObject) obj;
			PageEntity p = new PageEntity((long) object.get("pageID"),
					object.get("pageName").toString());
			pages.add(p);
		}
		map.put("pagesList", pages);
		if(map.size()!=0)
		{
			System.out.println("eeeeeeeeeeeeeee");
			return Response.ok(new Viewable("/jsp/adminPages", map)).build();
		}
			
		else
		{
			System.out.println("tttt");
			return Response.status(Response.Status.NOT_FOUND).entity
					("Not Found" ).build();
		}
		}catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return null;
	}
	@POST
	@Path("/advancedSearch")
	public Response advancedSearch(@FormParam("name")String name){
		System.out.println(name);
		String serviceUrl = "http://localhost:8888/rest/advancedSearch";
		String urlParameters = "name=" + name;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
		   "application/x-www-form-urlencoded;charset=UTF-8");
		Map<String,Vector<PageEntity>> passedPages = new HashMap<String,Vector<PageEntity>>();
		JSONParser parser = new JSONParser();
		try{
			JSONArray array=(JSONArray) parser.parse(retJson);
			Vector<PageEntity>pages = new Vector<PageEntity>();
			for(int i=0;i<array.size();i++){
				JSONObject object;
				object=(JSONObject) array.get(i);
				pages.add(PageEntity.parsePafeInfor(object.toJSONString()));
			}
			passedPages.put("pagesList", pages);
			if(pages.size()!=0)
				return Response.ok(new Viewable("/jsp/showPages", passedPages)).build();
			else 
				return Response.status(Response.Status.NOT_FOUND).entity
						("Page Is Not Found" ).build();
		}catch(ParseException e){
			e.printStackTrace();
		} 
		return null;
	}
	@POST
	@Path("/likePage")
	public String likePage(@FormParam("name") String pageName)
	{
		String urlParameters =  "name=" + pageName;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/likePage", urlParameters,
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
		System.out.println(pageName);
		return null;
	}
	@POST
	@Path("/likePost")
	public Response likePost(@FormParam ("key") int ID)
	{
		String urlParameters = "key=" + ID;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/likePost", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if(object.get("status").equals("success"))
				return Response.ok(new Viewable("/jsp/home")).build();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	@GET
	@Path("/newsfeed")
	public Response  newsfeed()
	{
		String urlParameters =  "email="+User.getCurrentActiveUser().getEmail(); 
		String retJson = Connection.connect(
				"http://localhost:8888/rest/viewNewsfeed", urlParameters,
				"POST", "application/x-wwwc-form-urlencoded;charset=UTF-8");
		Vector<Object>posts=new Vector<Object>();
		Map<String, Vector<Object>> map = new HashMap<String, Vector<Object>>();
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			System.out.println("try");
			obj = parser.parse(retJson);
			JSONArray array = (JSONArray)obj;
		for(int i=0;i<array.size();i++)
		{
			String s1 = array.get(i).toString() ;
			obj = parser.parse(s1);
			JSONObject object = (JSONObject) obj;
			int ID = Integer.parseInt(object.get("key").toString());
			int likes = Integer.parseInt(object.get("Likes").toString());
			//System.out.println(ID+"  "+object.get("post").toString()+"  "+likes);
			System.out.println("owner = " +object.get("owner"));
			UserPost p = new UserPost(ID,object.get("post").toString(),likes,
					object.get("owner").toString());
			posts.add(p);
		}
		for(int i = 0 ; i< posts.size();i++)
		{
			System.out.println (((Post) posts.get(i)).getID());
			System.out.println (((Post) posts.get(i)).getContent());
			System.out.println (((Post) posts.get(i)).getOwner());
		}
		map.put("postsList", posts);
		if(map.size()!=0)
		{
			return Response.ok(new Viewable("/jsp/newsfeed", map)).build();
		}
		else
		{
			return Response.status(Response.Status.NOT_FOUND).entity
					("Not Found" ).build();
		}
		}catch (ParseException e) {
		e.printStackTrace();
		}
		return null;
	}
}