package com.FCI.SWE.Controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
@Produces("text/html")
public interface AllPosts {
	public Response writePost(@FormParam("email") String email);
	public Response post(@FormParam("email") String email,@FormParam("privacy") String privacy,
			@FormParam("post") String post,@FormParam ("feeling") String feeling
			,@FormParam ("Hashtag") String Hashtag);


}
