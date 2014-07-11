// Define constants in this variable
var Constants = {
	"UserRestURL" : "/mobEdurest/user/",
	"MessageRestURL" : "/mobEdurest/message/"
};
/* For every message added, add the below code to load the function */
$(document).ready(init);
$(document).ready(handleSubmitMessage);

function init() {
	if (typeof jQuery != 'undefined') {
		console.log("jQuery library is loaded!");
	} else {
		alert("jQuery library is not found! Loading will not continue. "
				+ "Please check you have internet access "
				+ "and upgrade your browser if you are using a old version.");
	}
	$('#message').val("");
	$('#countdown').val("160");
	// /mobEdurest/user/*
	/*
	 * NOTE: This affects all the ajax calls hence jQuery recomends not to use
	 * it. $.ajaxSetup({ cache : false, url : "/sendSMS/", global : false, type :
	 * "POST", timeout : 300000 });
	 */
}

function handleSubmitMessage() {
	$("#postInfo").click(function() {
		// console.log("Current Button Value:" + $('#postInfo').val());
		if ("Submit Message" == $('#postInfo').val()) {
			$('#smscontent').slideUp('slow');
			$('#postInfo').val("Submit Another Message");
			$("#response").empty();

			submitMsgToUsers();
		} else if ("Submit Another Message" == $('#postInfo').val()) {
			$('#smscontent').slideDown('slow');

			/* Reset the values for next message submission */
			$('#message').val("");
			$('#countdown').val("160");
			$('#postInfo').val("Submit Message");
			$("#response").empty();
		}
		// console.log("Changed Button Value:" + $('#postInfo').val());
	});
}

function submitMsgToUsers() {
	/* Post request to get the result from the server */
	// console.log("Hey accessing Constants.UserRestURL" +
	// Constants.UserRestURL);
	$.get(Constants.UserRestURL + "userCount", sendMessageIteratively);
}

function sendMessageIteratively(numberOfUsers) {
	// console.log("Number of users after the call:" + numberOfUsers);
	for (var iCount = numberOfUsers; iCount > 0 ; iCount--) {
		// console.log("Sending SMS to the user:" + iCount);
		$('#userid').val(iCount);

		var url = Constants.MessageRestURL + "sendSMSToUsers.this";
		// console.log("URL:" + url);
		$.post(url, $('#sendsmsform').serialize(), appendResult);
	}
}

function appendResult(res) {
	// console.log("Here is the result:" + res);
	$("#response").append("<br />" + res);
}
