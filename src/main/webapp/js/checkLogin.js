
/* For every method added, add the below code to load the function */

$(document).ready(handleSubmit);

var expName, expId, expLogin;

function handleSubmit() {
	$("#loginBtn").click(
			function() {
				console.log("Login button was clicked");
				var expName = $.trim($('#username').val());
				var expPass = $.trim($('#userpassword').val());

				if (('undefined' == expName || expName.length == 0)
						|| ('undefined' == expPass || expPass.length == 0)) {
					alert("Please enter User Name / Password to login");
				} else {
					var url = Constants.ExpertRestURL + "checkExpert.this";
					$.post(url, $('#login').serialize(), checkresult);

				}
			});
}

function checkresult(expertObj) {
	// as a string
	console.log("inside check result:" + expertObj);
	if ("" != expertObj && expertObj.id != 0) {
		if (typeof expertObj != 'undefined') {
			id = expertObj.id;
			name = expertObj.name;
			loginId = expertObj.loginId;
			
			loadAllQuestions();
			closeForm();
		}
	} else {
		$('#message').clear();
		$('#message').append(LoginErrMsg);
	}
}
function appendResult(res) {
	// console.log("Here is the result:" + res);
	if (res == true) {// on success code

	} else {// on unsuccess code
	}
}

function submit() {
	/* Post request to get the result from the server */
	// console.log("Hey accessing Constants.UserRestURL" +
	// Constants.UserRestURL);
	$.get(Constants.UserRestURL + "userCount", sendMessageIteratively);
}

function sendMessageIteratively(numberOfUsers) {
	// console.log("Number of users after the call:" + numberOfUsers);
	for (var iCount = numberOfUsers; iCount > 0; iCount--) {
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
