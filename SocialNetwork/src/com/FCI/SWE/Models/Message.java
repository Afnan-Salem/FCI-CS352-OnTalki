package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

//subject
public class Message 
{
	private String name;
	private int ID;
	
	protected List <String> recievers = new ArrayList<String>();
	private static Message ActiveMessage;
	public Message() {
		// TODO Auto-generated constructor stub
	}
	public Message(String name) 
	{
		name = this.name;
	}
	public Message(int ID) 
	{
		ID = this.ID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public int getID() {
		return ID;
	}
	//	public void addd(String reciever)
//	{
//	      recievers.add(reciever); 
//	}
//	public void view()
//	{
//		for(int i = 0 ; i<recievers.size();i++)
//		{
//			System.out.println(i+"  -- "+recievers.get(i));
//		}
//	}
	public static Message getCurrentActiveMessage(){
		return ActiveMessage ;
	}
	public int saveMessage(String name) 
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("conversations");
		String admin = User.getCurrentActiveUser().getEmail();
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		int ConvID=list.size()+1;
		Message msgID=new Message(ConvID);
		try {
			Entity msg = new Entity("conversations", ConvID);
			msg.setProperty("name", name);
			msg.setProperty("admin", admin);
			datastore.put(msg);
			txn.commit();
		}finally{
			if (txn.isActive())
		        	txn.rollback();
		}
		return ConvID;
	}
	public  void searchReceivers(String ConvID) 
	{
		System.out.println(ConvID);
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Receiver");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(ConvID+"Hiii");
			if (entity.getProperty("CONID").toString().equals(ConvID))
			{
				System.out.println(ConvID);
				String receiver=entity.getProperty("receiver").toString();
				recievers.add(receiver);
				
			}}
		System.out.print(recievers.size());
		for(int i=0;i<recievers.size();i++){
			System.out.println(recievers.get(i));
		}
		
		
		}
	public  void notifyReceiver(String ConvID){
		UserEntity user=new UserEntity();
		//System.out.println("Conv"+getID());
		user.Update(recievers, ConvID);
	}
	
		
	}

