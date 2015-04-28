package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.Post;
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

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;
	private long id;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(){}
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public UserEntity(String name,String email) {
		this.name = name;
		this.email = email;
		
	}
	
	private void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

	
	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */
	public static List<Post>HashtagPosts(List<String> list){
		List<Post>posts=new ArrayList<Post>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
for(int i=0;i<list.size();i++){
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getKey().getId());
		Object obj;
			if (entity.getKey().getId()==Integer.parseInt(list.get(i))){
				System.out.println(entity.getKey().getId());
				System.out.println(entity.getProperty("owner").toString());
				
				Post post=new UserPost(entity.getProperty("content").toString(),
						entity.getProperty("owner").toString()
						);
				//System.out.println(UserPost.getContent());
				posts.add(post);
				
				
			}}}
for(int i=0;i<posts.size();i++){System.out.println(posts.get(i).getOwner()+"Hi");}
		return posts;
	}
	public static List<Post>getOwnerName(List<Post> list){
		List<Post>posts=new ArrayList<Post>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
for(int i=0;i<list.size();i++){
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("email").toString().equals(list.get(i).getOwner()))
			{System.out.println("HIIII owner");
				Post post=new UserPost(entity.getProperty("name").toString(),list.get(i).getOwner()
						,list.get(i).getContent());
				posts.add(post);
				
			}}}
for(int i=0;i<posts.size();i++){System.out.println(posts.get(i).toString());}
		return posts;
	}
	public static long getHashtagID( String name){
		long ID=0;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("Hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Hashtagname").toString().equals(name))
			{
				System.out.println(name +ID+"ID");
				ID=entity.getKey().getId();
				
			}}
		System.out.println(name +ID+"ID");

	return ID;
	}
	public static List<Post>SearchHashtagResults(String name){
		long HID=getHashtagID(name);
		List<Post> post=new ArrayList<Post>();
		List<String> list=new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("HashtagRelation");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		System.out.println(HID+"ID");
		for (Entity entity : pq.asIterable()) {
			if (Integer.parseInt(entity.getProperty("HashtagID").toString())==HID)
			{
				System.out.println(HID+"ID");
				list.add(entity.getProperty("PostID").toString());
				
			}}
	post=getOwnerName(HashtagPosts(list));
		
	for(int i=0;i<post.size();i++){System.out.println(post.get(i).toString());}
		return  post;
	}
public static List<UserEntity>SearchResults(String name){
	List<UserEntity> list=new ArrayList<UserEntity>();
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	Query gaeQuery = new Query("users");
	PreparedQuery pq = datastore.prepare(gaeQuery);

	for (Entity entity : pq.asIterable()) {
		if (entity.getProperty("email").toString().equals(name))
		{
			UserEntity returnedUser = new UserEntity(entity.getProperty(
					"name").toString(),(entity.getProperty("email").toString()));
			list.add(returnedUser);
			
		}}
	return  list;}


	public static UserEntity getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
				returnedUser.setId(entity.getKey().getId());
				return returnedUser;
			}
		}

		return null;
	}

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */

	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		Entity employee = new Entity("users", list.size() + 2);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		
		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;

	}
	
	public void Update(List <String> recievers,String ConvID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
			Transaction txn= datastore.beginTransaction();	
;
		

		//List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		//Entity newNotification= new Entity("Notification_",list.size()+1);
		try{
		for(int i=0;i<recievers.size();i++)
			{
			txn = datastore.beginTransaction();	

			Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			Entity newNotification= new Entity("Notifications",list.size()+1);
			if( !recievers.get(i).equals(User.getCurrentActiveUser().getEmail().toString())){
				
				System.out.print("H    "+recievers.get(i));
			newNotification.setProperty("sender", ConvID.toString());
			newNotification.setProperty("receiver", recievers.get(i));
			newNotification.setProperty("Msg", "You Have a Message");
			newNotification.setProperty("statuse", "unread");
			newNotification.setProperty("Type", "MessagesNotifications");
			datastore.put(newNotification);
			}
			txn.commit();
			//txn.rollback();
			}
		}
		finally{
			if (txn.isActive())
				txn.rollback();
		}
		
		
	}
	public boolean saveNotification(String Semail,String Remail,String Type,String status)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		User user=User.getCurrentActiveUser();
		Entity notification = new Entity("Notifications", list.size() + 1);
		notification.setProperty("sender", Semail);
		notification.setProperty("receiver", Remail);
		notification.setProperty("statuse", status);
		notification.setProperty("Type",Type);
		datastore.put(notification);
		//DatastoreService datastore1 = DatastoreServiceFactory
				//.getDatastoreService();
			return true;
	}
	}

