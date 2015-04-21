package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.FCI.SWE.ServicesModels.UserTimeline;
import com.FCI.SWE.ServicesModels.TimeLine;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class UserTimelineServices implements TimelineServices {
	@POST
	@Path("/viewUser")
	public String  viewprofile()
	{
		System.out.println("service");
		TimeLine t = new UserTimeline();
		return t.getPosts();
	}


}
