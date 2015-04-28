package com.FCI.SWE.Controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PrivateController implements PrivacyControllers {
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
		String serviceUrl = "http://localhost:8888/rest/Private";
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
				return Response.ok(new Viewable("/jsp/home")).build();
			} catch (ParseException e) {
			e.printStackTrace();
										}
		return null;
	}
}
