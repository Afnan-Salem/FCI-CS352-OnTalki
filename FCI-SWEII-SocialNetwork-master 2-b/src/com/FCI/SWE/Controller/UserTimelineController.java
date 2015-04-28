package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.Post;
import com.FCI.SWE.ServicesModels.UserPost;


@Path("/")
@Produces("text/html")
public class UserTimelineController implements TimelineController {
	@GET
	@Path("/ViewUserProfile")
	public Response  viewprofile()
	{
		String urlParameters =  "email="+User.getCurrentActiveUser().getEmail(); 
		String retJson = Connection.connect(
				"http://localhost:8888/rest/viewUser", urlParameters,
				"POST", "application/x-wwwc-form-urlencoded;charset=UTF-8");
		Vector<Object>userPosts=new Vector<Object>();
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
			userPosts.add(p);
		}
		for(int i = 0 ; i< userPosts.size();i++)
		{
		System.out.println (((Post) userPosts.get(i)).getID());
		System.out.println (((Post) userPosts.get(i)).getContent());
		}
		map.put("userPosts", userPosts);
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

}
