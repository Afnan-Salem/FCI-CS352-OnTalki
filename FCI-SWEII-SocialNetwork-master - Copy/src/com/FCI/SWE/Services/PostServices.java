package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public interface PostServices {
	@POST
	@Path("/POST")
	public String POST(@FormParam("email") String email,@FormParam("post") String post,
			@FormParam ("feeling") String feeling , @FormParam("privacy") String privacy);

}
