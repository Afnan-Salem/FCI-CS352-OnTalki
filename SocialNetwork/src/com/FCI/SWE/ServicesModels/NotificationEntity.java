package com.FCI.SWE.ServicesModels;
import java.util.ArrayList;

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
