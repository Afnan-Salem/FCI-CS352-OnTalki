package com.FCI.SWE.Models;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Notifications {
	public String type;
	//public String name;
	public long id;
	private static Notifications currentNotification;
	
	public Notifications(){
		
	}
	public Notifications(String type){
		//this.name=name;
		this.type=type;
	}
	public String getType() {
		return type;
	}
	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}
	public static Notifications getCurrentNotifications(){
		return currentNotification;
	}
	public static Notifications getNotification(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			currentNotification = new Notifications(object.get("type").toString());
			currentNotification.setId(Long.parseLong(object.get("id").toString()));
			return currentNotification;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
