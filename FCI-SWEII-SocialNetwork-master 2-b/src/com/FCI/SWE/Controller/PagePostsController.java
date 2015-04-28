package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.PagePost;
import com.FCI.SWE.ServicesModels.Post;

@Path("/")
@Produces("text/html")
public class PagePostsController implements AllPosts {

	@Override
	@POST
	@Path("/writePagePost")
	public Response writePost(@FormParam("email") String email){
		// TODO Auto-generated method stub
		System.out.println("write post");
		if (email.equals("NULL"))
		{
			
			email = User.getCurrentActiveUser().getEmail();
			System.out.println("email" + email);
		}
		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("email",email);
				return Response.ok(new Viewable("/jsp/PagePosts", map)).build();
	}

	@Override
	@POST
	@Path("/PagePost")
	public Response post(@FormParam("email") String email,@FormParam("privacy") String privacy,
			@FormParam("post") String post,@FormParam ("feeling") String feeling,
			@FormParam ("Hashtag") String Hashtag) {
		// TODO Auto-generated method stub
        System.out.println("Page Post");
		
		try{
		Post p = new PagePost(email,post,feeling,privacy,Hashtag);
		System.out.println("PPPPage PPPPost");
		return  p.savePost(email,post,feeling,privacy,Hashtag);}
		catch(Exception e)
		  {
		   e.printStackTrace();
		  }
		return null;
	}

}
