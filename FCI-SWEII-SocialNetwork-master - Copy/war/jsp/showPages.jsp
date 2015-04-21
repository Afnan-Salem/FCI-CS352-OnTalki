
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Pages</title>
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
<form action="/social/likePage" method="post">
<c:forEach items="${it.pagesList}" var="page">
<p><h3> Page Name: <c:out value="${page.name}" ></c:out></p>
<input type="submit" value="Like" /></h3>
<input type="hidden" name="name" value="${page.name}">
</c:forEach>


	</form>
</body>
</html>