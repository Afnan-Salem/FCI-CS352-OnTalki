package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;

import com.FCI.SWE.Services.TimelineServices;
import com.FCI.SWE.ServicesModels.PageTimeline;
import com.FCI.SWE.ServicesModels.TimeLine;
import com.FCI.SWE.ServicesModels.UserTimeline;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PageTimelineServices implements TimelineServices {
	@POST
	@Path("/viewPage")
	public String  viewprofile()
	{
		
				System.out.println("service");
				TimeLine t = new PageTimeline();
				return t.getPosts();

}
}
