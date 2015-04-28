
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

	@Path("/")
	@Produces("text/html")
	public class Notifications implements NotificationsController {
		@POST
		@Path("/ListA")
		public Response List(){
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
				System.out.print(object);
				obj1 = parser1.parse(retJson1);
				JSONObject object1 = (JSONObject) obj1;
			 	System.out.print(object1);
			 	System.out.print(object1);
			 			Map<String, String> map = new HashMap<String, String>();
			 			Map<String, String> map1 = new HashMap<String, String>();
			 			map.put("email",object.get("sender").toString());
			 			map1.put("Memail",object1.get("Msender").toString());
					    	map.put(null, null);
					    	map.putAll(map1);
					    	map.put("email",object.get("sender").toString());
					    	map.put("Memail",object1.get("Msender").toString());
					    	System.out.println("ALLLLLL");
							return Response.ok(new Viewable("/jsp/notifications", map)).build();
			}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
}
}