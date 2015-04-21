<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
<form action="/social/writeUserPost" method="post">
<input type="hidden" name="email" value="NULL"/>
<input type="submit" value="On Your Timeline" />
</form>
<button onclick="location.href='/social/searchFriend/' " >On Friends Timeline</button>
</body>
</html>
