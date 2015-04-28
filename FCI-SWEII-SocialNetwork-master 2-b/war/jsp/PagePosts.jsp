<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<form action="/social/PagePost" method="post">
<input type="hidden" name="email" value="${it.email}"/>
<h3>Write your status : </h3>
<textarea rows="4" cols="50" name="post"></textarea>
<h6>What are you feeling NOW</h6>
<select name="feeling" onchange="">
<option value="happy">happy</option>
<option value="sad">sad</option>
<option value="determined">determined</option>
<option value="bad">bad</option>
<option value="loved">loved</option>
<option value="amazing">amazing</option>
<option value="depressed">depressed</option>
<option value="worried">worried</option>

</select><br>
<h6>Hashtag </h6>
<input type="text" name="Hashtag" >
<br>
<h6>privacy </h6>
<select name="privacy" onchange="">
<option value="PublicController">public</option>
<option value="PrivateController">private</option>
</select>
<input type="submit"value="Post" />
</form>
<form action ="/social/ViewPage" method="post">
<input type="submit" value="Page Timeline">
</form>
</body>
</html>