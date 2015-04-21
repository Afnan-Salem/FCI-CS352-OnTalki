package com.FCI.SWE.Controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;

public interface PrivacyControllers 
{
	public abstract javax.ws.rs.core.Response doPrivacy(String timeline, String content,
			String feeling, String type,String Hashtag);
}
