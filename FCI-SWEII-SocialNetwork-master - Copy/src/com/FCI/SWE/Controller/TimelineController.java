package com.FCI.SWE.Controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
@Produces("text/html")
public interface TimelineController {
	public Response  viewprofile();

}
