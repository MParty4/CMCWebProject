<%@page language="java" import="cmc.mario.controllers.*" import="cmc.mario.entities.*" import="cmc.mario.interfaces.*" import="java.util.ArrayList"%>
<html>
<head>

<title>Edit User Form</title>
<style type="text/css">
body{
margin:0;
padding:0;
font-family:Titillium Web;
color:darkgrey;
background:#f3f3f3;
}
.container {
    max-width:960px;
    width:96%
}
header, footer {
    padding: 1em;
    color: white;
    background-color: black;
    clear: left;
    text-align: center;
}
.logo{
float:left;
margin-top:-30px;
url:logo.PNG;
}
nav{
float:right;
line-height:70px;
}
nav li{
display:inline-block;
padding:5px 20px;
margin-left:10px;
background:#ff4719;
line-height:normal;
}
nav li a{
	color: white;
	 text-decoration: none;
}
.content ul{
	list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
	clear: left;
    background-color: #f1f1c1;
}
 li{
float: left;
}
 li a, .dropbtn {
    display: inline-block;
    color: black;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

 li a:hover, .dropdown:hover .dropbtn {
    background-color: #ff4719;
}

 li.dropdown {
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f1f1c1;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    text-align: left;
}

.dropdown-content a:hover {background-color: #ff4719}

.dropdown:hover .dropdown-content {
    display: block;
}
table#t01{
color:black;
background-color:#f1f1c1;
}
h3{
color: black;
}
</style>
</head>
<body>
<header>
<div class="container">
<div class="logo">
	<img src="logo.PNG" width="65" alt="" title="">
</div>
<nav>
<li><a href="LogOff.jsp">Logout</a></li>
</nav>
<h1>Choose Your College</h1>
</div>
</header>
<!-- <form method="post" action="LogOff.jsp" name="LogOff">
    <input name="Logout" type="submit" value="Logout">
</form> -->
<div class="container">
<div class="content">
<ul>
<li class="dropdown">
<a href="Admin_Menu.jsp" class="dropbtn"><strong>Main Menu</strong></a>
    <div class="dropdown-content">
      <a href="ViewUniversities.jsp">Manage Universities</a>
      <a href="manage_Users.jsp">Manage Users</a>
    </div>
  </li>
</ul>
<br>
<h3>
Edit User form</h3><br>
<%
	AdminFuncController ac = (AdminFuncController)session.getAttribute("uc");
	String uname = request.getParameter("Username");
	String fname = request.getParameter("Firstname");
	String lname = request.getParameter("Lastname");
	String password = request.getParameter("Password");
	char type = request.getParameter("Type").charAt(0);
	char status = request.getParameter("Status").charAt(0);
	String anyErrors = request.getParameter("Error");
	if (anyErrors!=null&&anyErrors.equals("1")){
	out.print("Unable to edit user");}
%>
<br>
<form method="post" action="Edit_Action_Admin.jsp" name="editUser"><br>
<table style="text-align: left; width: 266px; height: 228px;"
border="1" id="t01" >
<tbody>
<tr>
<td style="vertical-align: top;">First Name<br>
</td>
<td style="vertical-align: top;"><input name="FirstName" value=<%=fname%>><br>
</td>
</tr>
<tr>
<tr>
<td style="vertical-align: top;">Last Name<br>
</td>
<td style="vertical-align: top;"><input name="LastName" value=<%=lname%>><br>
</td>
</tr>
<tr>
<td style="vertical-align: top;">Username<br>
</td>
<td style="vertical-align: top;"><input name = "Username" value=<%=uname%> readonly></td>
</tr>
<tr>
<td style="vertical-align: top;">Password<br>
</td>
<td style="vertical-align: top;"><input name="Password" value=<%=password%>> </td>
</tr>
<tr>
<td style="vertical-align: top;">Type<br>
</td>
<td style="vertical-align: top;"><input name="Type" value=<%=type%>> </td>
</tr>
<tr>
<td style="vertical-align: top;">Status<br>
</td>
<td style="vertical-align: top;"><input name="Status" value=<%=status%>> </td>
</tr>
<tr>
<td style="vertical-align: top; "><input value="Edit"
name="Edit" type="submit" style="color: white; background-color:black; padding:3px; float:left" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td style="vertical-align: top;"><input value="Cancel"
name="Reset" type="reset" style="color: white; background-color:black; padding:3px;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
</tr>
</tbody>
</table>
<br>
</form>
<br>
</div>
</div>
<footer>
Copyright &copy; MarioChoose.com
</footer>
</body>
</html>