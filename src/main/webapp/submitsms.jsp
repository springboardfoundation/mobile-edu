<%@ page language="java" errorPage="/defaulterror.html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Page to submit SMS</title>
<script language="javascript" type="text/javascript" src="js/utils.js"></script>
</head>
<body>
	<%
		int count = 160; //Count of the characters limit
	%>
	<form action="sendSMS" method="POST">
		<h3>Enter the SMS to be sent in the below text box:</h3>
		<h4>
			<font size="1">(Maximum characters: <%=count%>)
			</font>
		</h4>
		<br />
		<textarea name="message" cols="50" rows="10" maxlength="<%=count%>"
			onKeyDown="limitText(this.form.message,this.form.countdown,<%=count%>);"
			onKeyUp="limitText(this.form.message,this.form.countdown,<%=count%>);">
		</textarea>
		<br /> You have <input readonly type="text" name="countdown" size="3"
			value="<%=count%>"> characters left. <br /> <br />
		<input type="submit" value="Submit"> <br />
		<div id="response"></div>
	</form>
</body>
</html>