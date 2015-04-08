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
<h3>New Message </h3>
	<form action="/social/sendMessage" method="post">
		<h3>To: <input type="text" name="Memail" /></h3>
        <h3>Message: <input type="text" name="message" /></h3>
		<input type="submit"value="Send" />
	</form>
</body>
</html>