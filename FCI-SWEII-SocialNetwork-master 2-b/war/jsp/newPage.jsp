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
<form action="/social/page" method="post" >
<h3>Page Information</h3>

<h3>Page Name</h3><input type="text" name="name" />
<h3>Type<h3>
<select name="Type" onchange="">
<option value="Local Business or Place">Local Business or Place</option>
<option value="Company, Organization or Institution">Company, Organization or Institution</option>
<option value="Brand or Product">Brand or Product</option>
<option value="Artist, Band or Public Figure">Artist, Band or Public Figure</option>
<option value="Entertainment">Entertainment</option>
<option value="Cause or Community">Cause or Community</option>
</select>
<h3>Category<h3><select name="Category" onchange="">
<option value="Actor/Director">Actor/Director</option>
<option value="App Page">App Page</option>
<option value="Arts/Entertainment">Arts/Entertainment</option>
<option value="Athlete">Athlete</option>
<option value="Bank/Finance">Bank/Finance</option>
<option value="Bookstore">Bookstore</option>
<option value="Camera/Photo">Camera/Photo</option>
<option value="Community Organization">Community Organization</option>
<option value="Computers/Technology">Computers/Technology</option>
<option value="Chemicals">Chemicals</option>
<option value="Engineering/Constructions">Engineering/Constructions</option>
<option value="Government Organization">Government Organization</option>
<option value="Hospital/Health care">Hospital/Health care</option>
<option value="Insurance Company">Insurance Company</option>
<option value="Jewelry/Watches">Jewelry/Watches</option>
<option value="Musician/Band">Musician/Band</option>
<option value="Phone/Tablet">Phone/Tablet</option>
<option value="Photographer">Photographer</option>
<option value="School/Education">School/Education</option>
<option value="TV Station">TV Station</option>
</select>
<input type="submit"value="Get Started" />
</form>

</body>
</html>