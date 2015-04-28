package com.FCI.SWE.ServicesModels;

import java.util.List;

import javax.xml.ws.Response;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.Controller.PrivacyControllers;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class UserPost extends Post
{	
	private String feeling;
	
//	public UserPost(String id, String content){}
	public UserPost(String content) 
	{
		super();
		//this.content = content;
	}
	public UserPost(int id) {
		super();
	}
	public UserPost(String content, String owner) {
		super();
		this.content = content;
		this.owner = owner;
	}
	public UserPost(String email, String post, String feeling2, String privacy, String hashtag) {
		super();
	}
	public UserPost(String ownername, String owner, String content ) {
		super();
		this.content = content;
		this.owner = owner;
		this.ownername = ownername;
	}
	public UserPost(Privacy privacy)
	{
		super();
	}
	public UserPost(String timeline ,Privacy p)
	{
		this.timeline = timeline;
		this.Privacy = p;
	}
	public UserPost(int ID,String timeline,String content,String feeling ,String type,Privacy privacy
			,String Hashtag)
	{
		super(ID,timeline,content,type,privacy, Hashtag);
		this.feeling = feeling;
	}
	public UserPost(String timeline,String content,String feeling ,String type)
	{
		super(timeline,content,type);
		this.feeling = feeling;
	}
	public UserPost(int ID, String content, int likes, String owner) 
	{
		this.ID = ID;
		this.content = content;
		this.likes = likes;
		this.owner =owner;
	}
	public UserPost(int iD2, String content , int likes) {
		this.ID = iD2;
		this.content = content;
		this.likes = likes;
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}
	@Override
	public javax.ws.rs.core.Response savePost(String timeline,String content , String feeling ,
			String type,String Hashtag) 
	{
		Class<?> c = null;
		try
		{
			System.out.println("tryy");	
			 c = Class.forName("com.FCI.SWE.Controller."+type);
			 PrivacyControllers p = null;
			p = (PrivacyControllers) c.newInstance();
			return  p.doPrivacy(timeline, content, feeling, type,Hashtag);
		}
		catch(Exception e)
		{
			System.out.println("catch");
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String likePost(int ID) 
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			int id = (int) entity.getKey().getId();
			System.out.println("Hiiiiiiiid"+id);
			System.out.println("HiiiiiiiiD"+ID);
			if (id==ID &&entity.getProperty("likes")!=null) 
			{
				String s = entity.getProperty("likes").toString();
				int like=Integer.parseInt(entity.getProperty("likes").toString())+1;
				entity.setProperty("likes", 
						like);	
				System.out.print("Hiiiiiii"+like);
				datastore.put(entity);
			}
		}
	JSONObject obj=new JSONObject();
	obj.put("status", "success");
	return obj.toString();
	}
}
