<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p> Welcome ${it.name} </p>

<form action="/social/ListRequests" method="post">
<input type="submit"value="Requests" />
	</form>
	<br><a href="/social/search/">Search for your friends</a> <br>
<form action="/social/Signout" method="post">
<input type="submit"value="Signout" />
</form><br>
</body>
</html>