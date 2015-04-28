
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Home Page</title>

</head>
<body>
<p><h1> Welcome ${it.name} </h1></p>
<button onclick="location.href='/social/search/' " >Search</button>
<form action="/social/Signout" method="post">
<h3><input type="submit"value="Signout" /></h3>
	</form>
<form action="/social/ListRequests" method="post">
<h3><input type="submit"value="Notifications" /></h3>
	</form>
	<button onclick="location.href='/social/viewprofile/' " >View Profile</button>
	
<button onclick="location.href='/social/writeMessage/' " >Send Message</button>
<button onclick="location.href='/social/groupChat/' " >Group Chat</button>
<button onclick="location.href='/social/Pages' " >Pages</button>

<button onclick="location.href='/social/createPost/' " >Create Post</button>
<button onclick="location.href='/social/admin' " >Your Pages</button>
<button onclick="location.href='/social/newsfeed/' " >View NewsFeed</button>

</body>
</html>