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

import com.FCI.SWE.Models.Notifications;
import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@Path("/")
@Produces("text/html")
public class RequestsNotifications implements NotificationsController{
	@POST
	@Path("/ListR")
	public Response List(){
		String urlParameters = User.getCurrentActiveUser().getEmail();
		String retJson = Connection.connect(
		"http://localhost:8888/rest/requests", urlParameters,
		"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			System.out.print(object);
			if(object.get("sender")!=null&&object.get("Type").equals("RequestsNotifications")){
				Map<String, String> map = new HashMap<String, String>();
				map.put("email",object.get("sender").toString());
				System.out.println("RRRRRRRR");
				return Response.ok(new Viewable("/jsp/requests", map)).build();
			}
			
			}catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

}
