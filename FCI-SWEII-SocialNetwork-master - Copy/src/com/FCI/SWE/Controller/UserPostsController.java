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
import com.FCI.SWE.ServicesModels.Post;
import com.FCI.SWE.ServicesModels.UserPost;
@Path("/")
@Produces("text/html")
public class UserPostsController implements AllPosts {

	@Override
	@POST
	@Path("/writeUserPost")
	public Response writePost(@FormParam("email") String email) {
		// TODO Auto-generated method stub
		System.out.println("write user post");
		if (email.equals("NULL"))
		{
			
			email = User.getCurrentActiveUser().getEmail();
			System.out.println("email" + email);
		}
		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("email",email);
				return Response.ok(new Viewable("/jsp/posts", map)).build();
	}

	@Override
	@POST
	@Path("/UserPost")
	public Response post(@FormParam("email") String email,@FormParam("privacy") String privacy,
			@FormParam("post") String post,@FormParam ("feeling") String feeling,
			@FormParam ("Hashtag") String Hashtag) {
		// TODO Auto-generated method stub
        System.out.println("UserPost");
		
		try{
		Post p = new UserPost(email,post,feeling,privacy,Hashtag);
		System.out.println("UUUUser PPPPost");
		return  p.savePost(email,post,feeling,privacy,Hashtag);}
		catch(Exception e)
		  {
		   System.out.println("catch");
		   e.printStackTrace();
		  }
		return null;
	}

}
