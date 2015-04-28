package com.FCI.SWE.ServicesModels;

import javax.xml.ws.Response;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public abstract class Post 
{
	public Privacy Privacy;
	public int ID;
	public String content ;
	public String Hashtag ;
	public String owner;
	public Post(String content, String owner) {
		super();
		this.content = content;
		this.owner = owner;
	}
	public String timeline ;
	public String type;
	public String ownername;
	
	
	public int likes;
	
	public static Post currentActivePost;

	public Post() {}
	
	public Post(int iD) 
	{
		ID = iD;
	}
	public Post(Privacy privacy)
	{
		this.Privacy = privacy;
	}
	public Post(int ID,String timeline,String content,String type,Privacy privacy, 
			String Hashtag)
	{
		this.ID = ID;
		this.timeline = timeline;
		this.content =content;
		this.type = type;
		this.Privacy = privacy;
		this.Hashtag=Hashtag;
	}
	public Post(String ownername, String owner, String content ) {
		super();
		this.content = content;
		this.owner = owner;
		this.ownername = ownername;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public static void setCurrentActivePost(Post currentActivePost) {
		Post.currentActivePost = currentActivePost;
	}

	public Post(String content) 
	{
		this.content = content;
	}
	/*public Post(String timeline,String content,String type)
	{
		this.timeline = timeline;
		this.content =content;
		this.type = type;
	}*/
	public int getID() {
		return ID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public Privacy getPrivacy() {
		return Privacy;
	}
	public void setPrivacy(Privacy privacy) {
		Privacy = privacy;
	}
	public String getTimeline() {
		return timeline;
	}
	public void setTimeline(String timeline) {
		this.timeline = timeline;
	}
	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public static Post getCurrentActivePost(){
		return currentActivePost;
	}
	public abstract javax.ws.rs.core.Response savePost(String timeline,String content , String feeling ,
			String type,String Hashtag);
	public abstract String likePost(int ID);
}