package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
public class PageEntity {

	public long id;
	public String owner;
	public String name;
	public String type;
	public String category;
	public int likes;
	public int reach;
	
	public PageEntity() {
		// TODO Auto-generated constructor stub
	}
	public PageEntity(long id,String name){
		this.name=name;
		this.id = id;
	}
	public PageEntity(String owner, String name, String type, String category){
		this.owner=owner;
		this.name=name;
		this.type=type;
		this.category=category;
	}
	private void setId(long id){
		this.id = id;
	}
	public long getId(){
		return id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getLikes() {
		return likes;
	}
	/*public void setLikes(int likes) {
		this.likes = likes;
	}*/
	public int getReach() {
		return reach;
	}
	public void setReach(int reach) {
		this.reach = reach;
	}
	public Boolean savePage() 
		{
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Transaction txn = datastore.beginTransaction();
			Query gaeQuery = new Query("Pages");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			try {
				Entity Page = new Entity("Pages", list.size() + 1);
				Page.setProperty("owner", this.owner);
				Page.setProperty("name", this.name);
				Page.setProperty("type", this.type);
				Page.setProperty("category", this.category);
				Page.setProperty("likes",0);
				Page.setProperty("reach", this.reach);
				datastore.put(Page);
				txn.commit();
			}finally{
				if (txn.isActive())
			        	txn.rollback();
			}
			return true;
		}
	public static Vector<PageEntity> searchPage(String name){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Pages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Vector<PageEntity>returnedPages = new Vector<PageEntity>();
		for (Entity entity : pq.asIterable()) 
		{
			String currentPage=entity.getProperty("name").toString();
			if(currentPage.contains(name)){
				PageEntity page = new PageEntity(entity.getProperty("owner").toString(),
						entity.getProperty("name").toString(),
						entity.getProperty("type").toString(),
						entity.getProperty("category").toString());
				page.setId(entity.getKey().getId());
				returnedPages.add(page);
			}
		}
		return returnedPages;
	}
	public static PageEntity parsePafeInfor(String json){
		JSONParser parser=new JSONParser();
		try{
			JSONObject obj = (JSONObject) parser.parse(json);
			PageEntity page = new PageEntity();
			page.setId(Long.parseLong(obj.get("id").toString()));
			page.setOwner(obj.get("owner").toString());
			page.setName(obj.get("name").toString());
			return page;
		}catch(ParseException e){
			e.printStackTrace();
		}
		return null;
	}
}
