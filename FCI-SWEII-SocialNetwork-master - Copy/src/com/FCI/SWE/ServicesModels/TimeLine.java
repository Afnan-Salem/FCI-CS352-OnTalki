package com.FCI.SWE.ServicesModels;

import javax.xml.ws.Response;

import com.FCI.SWE.Controller.PrivacyControllers;

public abstract class TimeLine 
{
	public TimeLine(){}
	
	public abstract String getPosts();
}
