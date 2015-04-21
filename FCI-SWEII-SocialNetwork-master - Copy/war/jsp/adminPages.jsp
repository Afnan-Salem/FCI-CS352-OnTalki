<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@  taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core"  %>
   
          
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Friend</title>
<style>
  form{
          margin-top:100px;
          margin-bottom:100px;
          margin-right:300px;
          margin-left:300px;
          border:3px solid black ;
          padding: 9px 35px;
          background:lightblue;
          border-radius:20px;
          box-shadow:7px 7px 6px;
         }
</style>
</head>
<body>
 <c:forEach items="${it.pagesList}" var="pages"> 
 	<form action = "/social/writePagePost" method="post">
 	<input type="hidden" name="email" value="Null"/>
 	<input type="hidden" name="key" value=<c:out value="${pages.id}"></c:out> />
	<p><c:out value="${pages.name}"/></p>
	<input type="submit" value="view" />	
	</form>
 </c:forEach>
</body>
</html>