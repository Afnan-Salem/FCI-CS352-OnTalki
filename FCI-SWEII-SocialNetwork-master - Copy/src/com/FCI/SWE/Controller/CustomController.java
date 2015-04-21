package com.FCI.SWE.Controller;

import java.util.HashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import com.sun.javafx.collections.MappingChange.Map;

public class CustomController implements PrivacyControllers
	{
		@Override
		@POST
		@Path("/")
		public Response doPrivacy(String email, String post,
				String feeling, String privacy,String Hashtag) 
		{
			System.out.println("do privacy custom ");
			System.out.println(email);
			System.out.println(post);
			System.out.println(feeling);
			System.out.println(privacy);
			String serviceUrl = "http://localhost:8888/rest/custom";
			String urlParameters = "email=" + email +"&post="+post+"&feeling="+feeling+
					"&privacy="+privacy+"&Hashtag="+Hashtag;
			String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
					"application/x-www-form-urlencoded;charset=UTF-8");
			JSONParser parser = new JSONParser();
			Object obj;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeline", email);
	 		map.put("content",post);
	 		map.put("feeling", feeling);
	 		map.put("type",privacy);
	 		JSONObject object = null;
			try {
				obj = parser.parse(retJson);
				 object = (JSONObject) obj;
				
				} catch (ParseException e) {
				e.printStackTrace();
											}
			//if (object.get("Status").equals("OK"))
				// viewprivacy( email, privacy, post,feeling);
			
				return Response.ok(new Viewable("/jsp/chooseCustoms")).build();
		}
		
	}



