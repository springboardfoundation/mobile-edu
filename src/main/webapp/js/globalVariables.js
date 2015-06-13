/**
 * 
 */

var exp_id = 0;
var exp_name = 'Guest';
var exp_loginId = 'guest';
var LoginErrMsg = "Provided login credentials are invalid";

// Define constants in this variable
var Constants = {
	"UserRestURL" : "/mobEdurest/user/",
	"MessageRestURL" : "/mobEdurest/message/",
	"ExpertRestURL" : "/mobEdurest/expert/",
	"QuestionURL" : "/mobEdurest/question/",
	"AnswerURL" : "/mobEdurest/answer/"
};

function setExpName(name) {
	exp_name = name;
}

function setExpLoginId(loginId) {
	exp_loginId = loginId;
}

function setExpId(id) {
	exp_id = id;
}

function getExpName() {
	return exp_name;
}

function getExpLoginId() {
	return exp_loginId;
}

function getExpId() {
	return exp_id;
}