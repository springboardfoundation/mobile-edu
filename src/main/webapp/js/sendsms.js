/* For every message added, add the below code to load the function */
$(document).ready(init);
$(document).ready(handleSubmitMessage);

function init() {
	$('#message').val("");
	$('#countdown').val("160");
	$.ajaxSetup({
		cache : false,
		url : "/sendSMS/",
		global : false,
		type : "POST",
		timeout : 300000
	});
}

function handleSubmitMessage() {
	$("#postInfo").click(function() {
		// console.log("Current Button Value:" + $('#postInfo').val());
		if ("Submit Message" == $('#postInfo').val()) {
			$('#smscontent').slideUp('slow');
			$('#postInfo').val("Submit Another Message");
			$("#response").empty();

			/* Post request to get the result from the server */
			// console.log($('#message').val());
			$.post("/sendSMS", $('#sendsmsform').serialize(), appendResult);
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

function appendResult(res) {
	// console.log("Here is the result" + res);
	$("#response").append(res);
}
