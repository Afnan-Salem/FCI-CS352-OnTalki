
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
<form action="/social/sendConvMessage" method="post">
<input type="hidden" name="Memail" value="${it.ConvID}"/>
<input type="text"name="message" />
<input type="submit"value="Send" />
</form>
<form action="/social/addFriendToConv" method="post">
<input type="hidden" name="ConvID" value="${it.ConvID}"/>
<input type="submit"value="Add Friend To Chat" />
</form>


	</form>
</body>
</html>