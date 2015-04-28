package com.FCI.SWE.Services;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public interface NotificationsInterface {
	public String notifyUser();
	
}
