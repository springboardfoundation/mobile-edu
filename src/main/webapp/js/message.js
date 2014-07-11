function broadcastMessage(message) {
	var msgTxt = message.value;
	alert("The message to be sent: " + msgTxt);
	message.value = "";

	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById("response").innerHTML = xmlhttp.responseText;
		}
	}
	xmlhttp.open("POST", "sendSMS", true);
	// xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send("message=" + msgTxt);
}

function handleResponse() {
	if (xmlhttp.readyState == 4) {
		document.getElementById("response").innerHTML = xmlhttp.responseText;
	}
}}
