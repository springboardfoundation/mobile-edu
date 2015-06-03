function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}

function makeJsonAjaxPostCall(url, data, successFunction) {
	var argString = JSON.stringify(data);
	console.log("The data as string:" + data);

	var request = $.ajax({
		type : "POST",
		url : url,
		data : argString,
		contentType : "application/json"
	});

	request.done(successFunction);

	request.fail(function(jqXHR, textStatus) {
		alert("Request failed: " + textStatus);
	});

}
