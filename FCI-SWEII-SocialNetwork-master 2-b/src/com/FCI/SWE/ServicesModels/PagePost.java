package com.FCI.SWE.ServicesModels;

import java.util.List;

import javax.xml.ws.Response;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.Controller.PrivacyControllers;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class PagePost extends Post
{	
	private String feeling;
	
	public PagePost(String id, String content){}
	public PagePost(String content) 
	{
		super();
		//this.content = content;
	}
	public PagePost(int id) {
		super();
	}
	public PagePost(String email, String post, String feeling2, String privacy, String hashtag) {
		super();
	}
	public PagePost(Privacy privacy)
	{
		super();
	}
	public PagePost(String timeline ,Privacy p)
	{
		this.timeline = timeline;
		this.Privacy = p;
	}
	public PagePost(int ID,String timeline,String content,String feeling ,String type,Privacy privacy,String Hashtag)
	{
		super(ID,timeline,content,type,privacy,  Hashtag);
		this.feeling = feeling;
	}
	public PagePost(String timeline,String content,String feeling ,String type)
	{
		super(timeline,content,type);
		this.feeling = feeling;
	}
	public PagePost(int iD2, String content , int likes) {
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
		/*DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		Entity post = new Entity("posts",list.size()+1);
		post.setProperty("Type", "userPosts");*/
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
	public String likePost(int ID) {
		// TODO Auto-generated method stub
		return null;
	}		
}
