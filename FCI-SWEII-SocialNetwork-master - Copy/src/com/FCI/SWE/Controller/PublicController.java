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
import com.FCI.SWE.Services.Custom;
import com.FCI.SWE.Services.Privacy;
import com.FCI.SWE.ServicesModels.PageEntity;
import com.FCI.SWE.ServicesModels.Post;
//import com.FCI.SWE.ServicesModels.Public;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.UserPost;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PublicController implements PrivacyControllers
{
	@Override
	@POST
	@Path("/")
	public javax.ws.rs.core.Response doPrivacy(String email, String post,
			String feeling, String privacy,String Hashtag) 
	{
		System.out.println("do privacy public ");
		System.out.println(email);
		System.out.println(post);
		System.out.println(feeling);
		System.out.println(privacy);
		String serviceUrl = "http://localhost:8888/rest/public";
		String urlParameters = "email=" + email +"&post="+post+"&feeling="+feeling+
				"&privacy="+privacy+"&Hashtag="+Hashtag;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return  Response.ok(new Viewable("/jsp/entryPoint")).build();
			} catch (ParseException e) {
			e.printStackTrace();
										}
		return null;
	}
}
