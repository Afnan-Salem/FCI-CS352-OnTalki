<html>
<head>
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
<h3>Write Page Name</h3>
	<form action="/social/advancedSearch" method="post">
		<h3><p> Page Name : </p></h3>
		<input type="text" name="name" /> 
		<input type="submit"value="Search" />
	</form>
</body>
</html>