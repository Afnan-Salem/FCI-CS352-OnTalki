package com.FCI.SWE.ServicesModels;
import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class NotificationEntity {
	public String type;
	public long id;
	
	public NotificationEntity()
	{
		
	}
	public NotificationEntity(String type){
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
	

}
