package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.Post;
import com.FCI.SWE.ServicesModels.UserPost;

public class UserPostServices implements PostServices {
	@POST
	@Path("/POSTP")
	public String POST(@FormParam("email") String email,@FormParam("post") String post,
			@FormParam ("feeling") String feeling , @FormParam("privacy") String privacy,
			@FormParam ("Hashtag") String Hashtag) 
	{
		Post p = new UserPost(email,post,feeling,privacy,Hashtag);
		p.savePost(email,post,feeling,privacy,Hashtag);
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

}
